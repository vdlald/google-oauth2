package com.vladislav.oauth.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;

@Route("home")
@PageTitle("Home")
public class HomeView extends VerticalLayout {

  private final Button signIn = new Button("Sign In") {{
    addClickListener(event -> getUI().ifPresent(ui -> ui.navigate(LoginView.class)));
  }};
  private final Button signOut = new Button("Sign Out") {{
    addClickListener(event -> VaadinSession.getCurrent().getSession().invalidate());
  }};

  public HomeView() {
    final WrappedSession session = VaadinSession.getCurrent().getSession();

    final String accessToken = (String) session.getAttribute("accessToken");

    if (accessToken == null) {
      add(text("Welcome new user! This is a home page"), signIn);
    } else {
      final String tokenType = (String) session.getAttribute("tokenType");
      final String expiresIn = (String) session.getAttribute("expiresIn");

      add(text("Welcome user! This is a home page."), signOut);
      add(text("Access Token: " + accessToken));
      add(text("Token Type: " + tokenType));
      add(text("Expires In: " + expiresIn + " seconds"));
    }
  }

  private Div text(String text) {
    return new Div(new Text(text));
  }
}
