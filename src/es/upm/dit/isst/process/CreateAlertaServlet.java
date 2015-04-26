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
//function comprobar(palabra){
//	if(palabra=""){
//		alert("el hueco esta vacio")
//	}
//}

public class CreateAlertaServlet extends HttpServlet {

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
		boolean cardiaco = false;
		// boolean tension = false;
		boolean glucosa = false;
		boolean acele = false;
		boolean gases = false;
		boolean incre = false;
		boolean co = false;
		boolean alarmas = false;
		boolean Nvacio = true;

		String summary ="tacc-isst-2015@appspot.com"; // "tacc-2015@appspot.com";// "tele-asistencias-isst-2015@appspot.com";
		// checkNull(req.getParameter("summary"));//"tacc-2015@appspot.com";//
		String areaMSG2="";
		String areaMSG = "evento(" + req.getParameter("user") + ";"
				+ req.getParameter("rit") + ";" + req.getParameter("tenMAX")
				+ ";" + req.getParameter("tenMIN") + ";"
				+ req.getParameter("glu") + ";" + req.getParameter("ace") + ";"
				+ req.getParameter("lat") + ";" + req.getParameter("lon") + ";"
				+ req.getParameter("gas") + ";" + req.getParameter("tem") + ";"
				+ req.getParameter("TEMP") + ";" + req.getParameter("co2")
				+ ";" + req.getParameter("alarma") + ")";
		// String areaMSG2 = "alarma(" + req.getParameter("user") + ";1;2;"
		// + req.getParameter("lat") + ";" + req.getParameter("lon") + ")";

		// String areaMSG = checkNull(req.getParameter("description"));
		if (Integer.parseInt(req.getParameter("alarma")) == -1) {
			avisos = true;
		} else {
			if (Integer.parseInt(req.getParameter("rit")) < 60
					|| Integer.parseInt(req.getParameter("rit")) > 160) {
				cardiaco = true;
			}
			// if ((Integer.parseInt(req.getParameter("tenMAX")) < 10 || Integer
			// .parseInt(req.getParameter("tenMAX")) > 13)
			// && (Integer.parseInt(req.getParameter("tenMIN")) < 6 || Integer
			// .parseInt(req.getParameter("tenMIN")) > 8)) {
			// tension = true;
			// }
			if (Integer.parseInt(req.getParameter("glu")) < 70
					|| Integer.parseInt(req.getParameter("glu")) > 100) {
				glucosa = true;
			}
			if (Integer.parseInt(req.getParameter("ace")) != 0) {
				acele = true;
			}
			if (Integer.parseInt(req.getParameter("gas")) != 0) {
				gases = true;
			}
			if (Integer.parseInt(req.getParameter("tem")) != 0) {
				incre = true;
			}
			if (Integer.parseInt(req.getParameter("co2")) < 23
					|| Integer.parseInt(req.getParameter("co2")) > 29) {
				co = true;
			}

			if (Integer.parseInt(req.getParameter("alarma")) != 0) {
				alarmas = true;
			}
			if(req.getParameter("lat")==null || req.getParameter("lat")==""){
				Nvacio=false;
			}
			if(req.getParameter("lon")==null || req.getParameter("lon")==""){
				Nvacio=false;
			}
		}
		// incendio
		if (avisos) {
			areaMSG2 = "alarma(" + req.getParameter("user") + ",0,1,"
					+ req.getParameter("lat") + "," + req.getParameter("lon")
					+ ")";
			// System.out.println(areaMSG);
		} else {
			if (alarmas) {
				if ((incre) && (gases)) {
					areaMSG2 = "alarma(" + req.getParameter("user") + ",3,"
							+ req.getParameter("alarma") + ","
							+ req.getParameter("lat") + ","
							+ req.getParameter("lon") + ")";
					// System.out.println(areaMSG);
				} else {
					if (cardiaco && acele) {
						areaMSG2 = "alarma(" + req.getParameter("user") + ",2,"
								+ req.getParameter("alarma") + ","
								+ req.getParameter("lat") + ","
								+ req.getParameter("lon") + ")";
						// System.out.println(areaMSG);
					} else {
						if ((acele && Integer.parseInt(req.getParameter("rit")) < 60)
								&& (glucosa || co)) {
							areaMSG2 = "alarma(" + req.getParameter("user")
									+ ",1," + req.getParameter("alarma") + ","
									+ req.getParameter("lat") + ","
									+ req.getParameter("lon") + ")";
							// System.out.println(areaMSG);
						}
					}
				}
			}
		}

		System.out.println(areaMSG);
		JID recipientJID = new JID(summary);
		Message response = new MessageBuilder()
				.withMessageType(MessageType.CHAT)
				.withRecipientJids(recipientJID).withBody(areaMSG).build();
		System.out.print(areaMSG);
		
		if (areaMSG2 != "" && alarmas) {// Send the alarma
			Message response2 = new MessageBuilder()
			.withMessageType(MessageType.CHAT)
			.withRecipientJids(recipientJID).withBody(areaMSG2).build();
			boolean messageSent = false;
			SendResponse status = xmppService.sendMessage(response2);
			messageSent = (status.getStatusMap().get(recipientJID) == SendResponse.Status.SUCCESS);
			resp.sendRedirect("/inicio");
		} 
		if (areaMSG != ""&& Nvacio) {// Send the evento
			boolean messageSent = false;
			SendResponse status = xmppService.sendMessage(response);
			messageSent = (status.getStatusMap().get(recipientJID) == SendResponse.Status.SUCCESS);
			resp.sendRedirect("/inicio");
		} else {
			resp.sendRedirect("/boton.html");
		}
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
