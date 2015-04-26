package es.upm.dit.isst.process;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.MessageType;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

//import es.upm.dit.isst.process.dao.TaccDAO;
//import es.upm.dit.isst.process.dao.TaccDAOImpl;

public class ROJOServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final XMPPService xmppService = XMPPServiceFactory
			.getXMPPService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		System.out.println("Creando nueva alerta");

		RequestDispatcher view = req.getRequestDispatcher("Inicio.jsp");
		//resp.setHeader("REFRESH", "5");
		view.forward(req, resp);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		boolean avisos = false;
		boolean vacio = false;

		String summary = "tacc-isst-2015@appspot.com";//"tacc-isst-2015@appspot.com"; // "xmpp-isst-2015@appspot.com";//"tele-asistencias-isst-2015@appspot.com";
		// checkNull(req.getParameter("summary"));//
		 String areaMSG = "alarma(" + req.getParameter("usr") + ";0;23;42;-3)";
		 String areaMSG2= "evento(" + req.getParameter("usr") + ";230;12;7;87;1;42;-3;0;0;37;24;23)";
		// String areaMSG = checkNull(req.getParameter("description"));
		if (Integer.parseInt(req.getParameter("alarma")) == -1) {
			avisos = true;
		}
		if(req.getParameter("lat")==null || req.getParameter("lat")==""){
			vacio=true;
		}
		if(req.getParameter("lon")==null || req.getParameter("lon")==""){
			vacio=true;
		}
			

		System.out.println(areaMSG);
		JID recipientJID = new JID(summary);
		Message response = new MessageBuilder()
				.withMessageType(MessageType.CHAT)
				.withRecipientJids(recipientJID).withBody(areaMSG).build();
		System.out.print(areaMSG);
		if (areaMSG != "" ) {// Send the message
			boolean messageSent = false;
			SendResponse status = xmppService.sendMessage(response);
			messageSent = (status.getStatusMap().get(recipientJID) == SendResponse.Status.SUCCESS);
			resp.sendRedirect("/inicio");
		} 
		if (areaMSG2 != "" ) {// Send the message
			Message response2 = new MessageBuilder()
			.withMessageType(MessageType.CHAT)
			.withRecipientJids(recipientJID).withBody(areaMSG2).build();
			boolean messageSent = false;
			SendResponse status = xmppService.sendMessage(response2);
			messageSent = (status.getStatusMap().get(recipientJID) == SendResponse.Status.SUCCESS);
			resp.sendRedirect("/inicio");
		}else {
			resp.sendRedirect("/");
		}
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
