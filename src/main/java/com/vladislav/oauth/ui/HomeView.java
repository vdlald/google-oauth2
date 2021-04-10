package com.vladislav.oauth.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vladislav.oauth.components.googleclient.GoogleClient;
import com.vladislav.oauth.pojo.GoogleProfileResponse;
import com.vladislav.oauth.utils.VaadinSessionWrapper;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Route("home")
@PageTitle("Home")
@RequiredArgsConstructor
public class HomeView extends VerticalLayout {

  private final GoogleClient googleClient;

  private Button signIn;
  private Button signOut;
  private Div welcomeText;

  @PostConstruct
  public void init() {
    // init ui components
    signIn = new Button("Sign In");
    signOut = new Button("Sign Out");
    welcomeText = new Div();

    // set click listeners
    signIn.addClickListener(this::onSignIn);
    signOut.addClickListener(this::onSignOut);

    add(welcomeText);

    if (VaadinSessionWrapper.isAuth()) {
      loggedHome();
    } else {
      anonymousHome();
    }
  }

  private void anonymousHome() {
    welcomeText.setText("Welcome new user! This is a home page");
    add(signIn);
  }

  private void loggedHome() {
    final GoogleProfileResponse profile = googleClient.getProfile();

    final String message = String.format("Welcome %s! This is a home page", profile.getName());
    welcomeText.setText(message);

    add(signOut);
  }

  private void onSignIn(ClickEvent<Button> event) {
    getUI().ifPresent(ui -> ui.navigate(LoginView.class));
  }

  private void onSignOut(ClickEvent<Button> event) {
    VaadinSession.getCurrent().getSession().invalidate();
  }
}
