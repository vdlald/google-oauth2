package com.vladislav.oauth.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import javax.servlet.http.HttpServletResponse;

@Tag(Tag.DIV)
public class RouteNotFoundError extends Component implements HasErrorParameter<NotFoundException> {

  @Override
  public int setErrorParameter(
      BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter
  ) {
    final String text = "Could not navigate to '" + event.getLocation().getPath() + "'";
    getElement().setText(text);
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
