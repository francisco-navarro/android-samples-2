<%@page import="com.alopec.rpg.world.mobiles.PlayerMobile"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.alopec.rpg.ws.servlet.beans.Conexion"%>
<%@ page import="com.alopec.rpg.ws.servlet.WorldServlet" %>
<html>
<head>
</head>
<body>
	<div style="width:300px;"><h2>Server is online.</h2>
		<div>Current users <%=WorldServlet.getInstance().getNumberConnections() %></div>
		<div style="border:1px solid green;">	
		<table>
		<%		
			for(Conexion con: WorldServlet.getInstance().getListaConexiones()){
				out.write("\n<tr><td>"+con.toString()+"</td></tr>");
			}
		%>
		</table>
		</div>
		<div>Map</div>
		<div style="border:1px solid green;">	
		<table width="100%">
		<%		
		
		 	HashMap<String,PlayerMobile> map=WorldServlet.getInstance().getWorldMobiles().getListaMobilesPlayers();	 	
			for(PlayerMobile player:map.values()){
				out.write("\n<tr>");
				out.write("<td>"+player.getLogin()+"</td>");
				out.write("<td>["+player.getHealth()+"]</td>");
				out.write("<td align=\"right\">("+player.getX()+","+player.getY()+")</td>");
				out.write("\n</tr>");
			}
		%>
		</table>
		</div>
	</div>
</body>
</html>