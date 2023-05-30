package org.weykhans

import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.web.context.request.RequestContextHolder


public class LoginEventListener implements ApplicationListener <AuthenticationSuccessEvent> {

    @Override
    void onApplicationEvent(final AuthenticationSuccessEvent event) {
		def session = RequestContextHolder.currentRequestAttributes().getSession();
        def user = event.getAuthentication().getPrincipal();
        
        /*
        Alumnos alumno = Alumnos.find("FROM Alumnos as a WHERE a.matricula='"+username+"'");
        if (alumno.size()){
			username = alumno.nombre;
        }
        */
		//session.setAttribute("user", username);
		//session.setAttribute("pass", password);
		session.setAttribute("redirect", "/login/checkTime")
    }

}
