package pl.wojtek.system_ksiegowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController
{
    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request)
    {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exceptionType = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        Object uri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Object servletName = request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
        model.addAttribute("message", message);
        model.addAttribute("errorCode", status);
        model.addAttribute("exception", exception);
        model.addAttribute("exceptionType", exceptionType);
        model.addAttribute("servletName", servletName);
        model.addAttribute("uri", uri);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
