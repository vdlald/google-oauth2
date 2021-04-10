package com.vladislav.oauth.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vladislav.oauth.components.googleclient.GoogleClient;
import com.vladislav.oauth.pojo.GoogleProfileResponse;
import com.vladislav.oauth.utils.VaadinSessionWrapper;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Route("signin")
@PageTitle("Login")
@RequiredArgsConstructor
public class LoginView extends VerticalLayout {

  @Value("${secret.redirect_uri}")
  private String redirectUri;

  @Value("${secret.client_id}")
  private String clientId;

  private final GoogleClient googleClient;

  @PostConstruct
  public void init() {
    // set style
    addClassName("login-view");
    setSizeFull();
    setAlignItems(Alignment.CENTER);
    setJustifyContentMode(JustifyContentMode.CENTER);

    add(new H1("Sign in"));

    if (VaadinSessionWrapper.isAuth()) {
      final GoogleProfileResponse profile = googleClient.getProfile();

      add(new Div(new Text("You are already signed in as " + profile.getName())));

      final Button home = new Button("Return to the Home page");
      home.addClickListener(this::onHome);
      add(home);
    } else {
      final String href = "https://accounts.google.com/o/oauth2/v2/auth"
          + "?scope=https://www.googleapis.com/auth/userinfo.profile&"
          + "access_type=online&"
          + "include_granted_scopes=true&"
          + "response_type=code&"
          + "redirect_uri=" + redirectUri + "&"
          + "client_id=" + clientId;

      final Anchor signIn = new Anchor();
      signIn.setText("Sign In via Google");
      signIn.setHref(href);

      add(signIn);
    }
  }

  private void onHome(ClickEvent<Button> event) {
    getUI().ifPresent(ui -> ui.navigate(HomeView.class));
  }
}
