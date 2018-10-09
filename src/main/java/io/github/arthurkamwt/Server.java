package io.github.arthurkamwt;

import io.github.arthurkamwt.servlet.TestServlet;
import io.github.arthurkamwt.servlet.TestWebSocket;
import javax.servlet.ServletContextEvent;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import org.apache.catalina.Context;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.websocket.server.Constants;
import org.apache.tomcat.websocket.server.WsContextListener;

public class Server {

  public static void main(String[] args) throws Exception {
    final Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);

    final Context context = tomcat.addContext("", null);
    context.addApplicationListener(Config.class.getName());

    Tomcat.addServlet(context, "DefaultServlet", new DefaultServlet());
    context.addServletMapping("/", "DefaultServlet");

    Tomcat.addServlet(context, "TestServlet", new TestServlet());
    context.addServletMapping("/test", "TestServlet");

    tomcat.start();
    tomcat.getServer().await();
  }

  public static class Config extends WsContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
      super.contextInitialized(sce);
      ServerContainer sc = (ServerContainer) sce.getServletContext().getAttribute(
          Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE
      );
      try {
        sc.addEndpoint(TestWebSocket.class);
      } catch (DeploymentException e) {
        throw new IllegalArgumentException(e);
      }
    }
  }

}
