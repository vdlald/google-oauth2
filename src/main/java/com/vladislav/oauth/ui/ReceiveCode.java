package com.vladislav.oauth.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.SneakyThrows;

@Route("code")
@PageTitle("Code")
public class ReceiveCode extends VerticalLayout {

  public ReceiveCode() {

    add(new Text("Please wait"));

    UI.getCurrent().getPage().executeJs("return window.location.href")
        .then(String.class, s -> {
          final URL url = url(s);
          final Map<String, String> query = splitQuery(url);

          if (query.containsKey("error")) {
            final String error = query.get("error");
            System.out.println(error);
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
        });
  }

  private static Map<String, String> splitQuery(URL url) {
    Map<String, String> query_pairs = new LinkedHashMap<>();
    String query = url.getRef();
    String[] pairs = query.split("&");
    for (String pair : pairs) {
      int idx = pair.indexOf("=");
      query_pairs.put(
          URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
          URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
    }
    return query_pairs;
  }

  @SneakyThrows
  private static URL url(String s) {
    return new URL(s);
  }
}
