package com.vladislav.oauth.ui;

import static com.vladislav.oauth.utils.UtilsMethods.splitQueryParams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vladislav.oauth.components.googleclient.GoogleClient;
import com.vladislav.oauth.models.GoogleAccount;
import com.vladislav.oauth.models.GoogleToken;
import com.vladislav.oauth.models.UserEntity;
import com.vladislav.oauth.pojo.ExchangeTokenResponse;
import com.vladislav.oauth.pojo.GoogleProfileResponse;
import com.vladislav.oauth.services.UsersService;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route("code")
@PageTitle("Code")
@RequiredArgsConstructor
public class ReceiveCode extends VerticalLayout {

  private final GoogleClient googleClient;
  private final ObjectMapper objectMapper;
  private final UsersService usersService;

  private Text text;
  private Button returnHome;

  @PostConstruct
  public void init() {
    text = new Text("Please wait...");

    returnHome = new Button("Return to the Home page");
    returnHome.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate(HomeView.class)));
    returnHome.setVisible(false);

    add(text, returnHome);

    UI.getCurrent().getPage().executeJs("return window.location.href")
        .then(String.class, this::handleAuthorization);
  }

  private void handleAuthorization(String href) {
    final URL url = url(href);
    final Map<String, String> query = splitQueryParams(url.getQuery());

    if (query.containsKey("error")) {
      // handle error
      final String error = query.get("error");

      log.error("Error SignIn: {}", error);
      text.setText("Login failed.");

      returnHome.setVisible(true);
    } else {
      final String code = query.get("code");

      final ExchangeTokenResponse token = googleClient.exchangeToken(code);

      // save data to session
      final WrappedSession session = VaadinSession.getCurrent().getSession();
      session.setAttribute("accessToken", token.getAccessToken());
      session.setAttribute("tokenType", token.getTokenType());
      session.setAttribute("expiresIn", token.getExpiresIn());

      final GoogleProfileResponse profile = googleClient.getProfile();
      final GoogleToken googleToken = objectMapper.convertValue(token, GoogleToken.class);
      final GoogleAccount googleAccount = objectMapper.convertValue(profile, GoogleAccount.class);
      googleAccount.setToken(googleToken);

      final UserEntity user;
      final Optional<UserEntity> optional = usersService.findByGoogleAccountId(profile.getId());
      if (optional.isPresent()) {
        user = usersService.save(optional.get().setGoogleAccount(googleAccount));
      } else {
        user = usersService.save(new UserEntity().setGoogleAccount(googleAccount));
      }

      session.setAttribute("userId", user.getId());

      // navigate to home
      UI.getCurrent().navigate(HomeView.class);
    }
  }

  @SneakyThrows
  private static URL url(String s) {
    return new URL(s);
  }
}
