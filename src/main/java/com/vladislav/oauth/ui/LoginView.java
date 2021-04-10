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

@Route("signin")
@PageTitle("Login")
@RequiredArgsConstructor
public class LoginView extends VerticalLayout {

  private final GoogleClient googleClient;
  private final String googleSignInHref;

  @PostConstruct
  public void init() {
    add(new H1("Sign in"));

    if (VaadinSessionWrapper.isAuth()) {
      logged();
    } else {
      anonymous();
    }
  }

  private void anonymous() {
    final Anchor signIn = new Anchor();
    signIn.setText("Sign In via Google");
    signIn.setHref(googleSignInHref);

    add(signIn);
  }

  private void logged() {
    final GoogleProfileResponse profile = googleClient.getProfile();

    add(new Div(new Text("You are already signed in as " + profile.getName())));

    final Button home = new Button("Return to the Home page");
    home.addClickListener(this::onHome);
    add(home);

  }

  private void onHome(ClickEvent<Button> event) {
    getUI().ifPresent(ui -> ui.navigate(HomeView.class));
  }
}
