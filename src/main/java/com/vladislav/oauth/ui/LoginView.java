package com.vladislav.oauth.ui;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("signin")
@PageTitle("Login")
public class LoginView extends VerticalLayout {

  private final Anchor signIn = new Anchor() {{
    setText("Sign In via Google");
    setHref("https://accounts.google.com/o/oauth2/v2/auth" +
        "?scope=https://www.googleapis.com/auth/userinfo.profile&"
        + "include_granted_scopes=true&"
        + "response_type=token&"
        + "redirect_uri=https://localhost:8443/code&"
        + "client_id=421243574159-aoaeno0dq8003a6cq24e6v72smtsc1qk.apps.googleusercontent.com");
  }};

  public LoginView() {
    addClassName("login-view");
    setSizeFull();
    setAlignItems(Alignment.CENTER);
    setJustifyContentMode(JustifyContentMode.CENTER);

    add(new H1("OAuth2.0 Google"), signIn);
  }
}
