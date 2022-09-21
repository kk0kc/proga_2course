package pupu;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "DopServlet", urlPatterns = "/list")
public class DopServlets extends HttpServlet {

    private static class ContentHolder {
        private final String previewResource;
        private final String mapping;

        public ContentHolder(String previewResource, String mapping) {
            this.previewResource = previewResource;
            this.mapping = mapping;
        }

        public String getPreviewResource() {
            return previewResource;
        }

        public String getMapping() {
            return mapping;
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, ? extends ServletRegistration> servletRegistrationMap = request.getServletContext().getServletRegistrations();

        List<? extends ServletRegistration> contentRegistrations = servletRegistrationMap.values().stream()
                .filter(this::checkIfContentUnitInstance)
                .collect(Collectors.toList());

        List<ContentHolder> contentHolders = contentRegistrations.stream()
                .map(reg -> new ContentHolder(
                        getResourcePath(reg),
                        reg.getMappings().stream().findAny().orElseThrow(() ->
                                new RuntimeException("Servlet mappings not found"))
                )).collect(Collectors.toList());

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>List</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<center><h1>Now choose!</h1></center>");
        out.println("<br>");

        contentHolders.forEach(contentHolder -> {
            out.println("<center><p>" + readResource(contentHolder.getPreviewResource()) + "</p></center> ");
            out.println("<center><form action=\"" + contentHolder.getMapping() + "\"><button>Link!</button></form></center>");
        });

        out.println("</body>");
        out.println("</html>");
    }

    private String readResource(String resourcePath) {
        try {
            return new String(getServletContext().getResourceAsStream(resourcePath).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private <M extends ServletRegistration> String getResourcePath(M servletReg) {
        return  getServletClass(servletReg).getAnnotation(ContentServlet.class).resourcePath();
    }

    private <M extends ServletRegistration> boolean checkIfContentUnitInstance(M servletReg) {
        return  getServletClass(servletReg).isAnnotationPresent(ContentServlet.class);
    }

    private <M extends ServletRegistration> Class<?> getServletClass(M servletReg) {

        String className = servletReg.getClassName();
        Class<?> servletClass;
        try {
            servletClass = this.getClass().getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return servletClass;
    }
}
