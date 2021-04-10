package com.vladislav.oauth.ui;

import static com.vladislav.oauth.utils.UtilsMethods.splitQueryParams;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import java.net.URL;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route("code")
@PageTitle("Code")
public class ReceiveCode extends VerticalLayout {

  private final Text text = new Text("Please wait...");
  private final Button returnHome = new Button("Return to the Home page") {{
    getUI().ifPresent(ui -> ui.navigate(HomeView.class));
    setVisible(false);
  }};

  public ReceiveCode() {
    add(text, returnHome);

    UI.getCurrent().getPage().executeJs("return window.location.href")
        .then(String.class, this::handleAuthorization);
  }

  private void handleAuthorization(String href) {
    final URL url = url(href);
    final Map<String, String> query = splitQueryParams(url.getRef());

    if (query.containsKey("error")) {
      // handle error
      final String error = query.get("error");

      log.error("Error SignIn: {}", error);
      text.setText("Login failed.");

      returnHome.setVisible(true);
    } else {
      // get data
      final String accessToken = query.get("access_token");
      final String tokenType = query.get("token_type");
      final String expiresIn = query.get("expires_in");

      // save data to session
      final WrappedSession session = VaadinSession.getCurrent().getSession();
      session.setAttribute("accessToken", accessToken);
      session.setAttribute("tokenType", tokenType);
      session.setAttribute("expiresIn", expiresIn);

      // navigate to home
      UI.getCurrent().navigate(HomeView.class);
    }
  }

  @SneakyThrows
  private static URL url(String s) {
    return new URL(s);
  }
}
