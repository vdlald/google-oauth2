package com.vladislav.oauth.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vladislav.oauth.pojo.GoogleProfile;
import java.io.IOException;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

@Route("home")
@PageTitle("Home")
public class HomeView extends VerticalLayout {

  private final Button signIn = new Button("Sign In") {{
    addClickListener(event -> getUI().ifPresent(ui -> ui.navigate(LoginView.class)));
  }};
  private final Button signOut = new Button("Sign Out") {{
    addClickListener(event -> VaadinSession.getCurrent().getSession().invalidate());
  }};

  @SneakyThrows
  public HomeView() {
    final WrappedSession session = VaadinSession.getCurrent().getSession();

    final String accessToken = (String) session.getAttribute("accessToken");

    if (accessToken == null) {
      add(text("Welcome new user! This is a home page"), signIn);
    } else {
      final String tokenType = (String) session.getAttribute("tokenType");
      final String expiresIn = (String) session.getAttribute("expiresIn");

      final GoogleProfile profile = getProfile(accessToken);

      add(text(String.format("Welcome %s! This is a home page.", profile.getName())), signOut);
      add(text("Access Token: " + accessToken));
      add(text("Token Type: " + tokenType));
      add(text("Expires In: " + expiresIn + " seconds"));
    }
  }

  private Div text(String text) {
    return new Div(new Text(text));
  }

  private GoogleProfile getProfile(String accessToken) throws IOException {
    final OkHttpClient client = new OkHttpClient();
    final Request request = new Builder()
        .url("https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + accessToken)
        .get()
        .build();

    Response response = client.newCall(request).execute();

    final String json = response.body().string();

    final ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, GoogleProfile.class);
  }
}
