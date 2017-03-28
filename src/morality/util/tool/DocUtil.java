package morality.util.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
* @desc 导出word,添加模版
* @author yangbo
*/
public class DocUtil {
	public static final String WORD_TEMPLATE = "/model.ftl";
	public static final String WORD_TEEPLATE = "/saferecord.ftl";
    public static final String TEMPLATE_PATH = "/resource/morality/module";
    public static final String PREVIEW_DOC = "/resource/morality/module/model.docx";
    public static final String SAFE_DOC ="/resource/morality/module/saferecord.docx";
    
    /**
     * @param request
     * @param temp
     * @return
     * @throws IOException Template 
     */
    public static Template configTemplate(HttpServletRequest request, String temp) throws IOException {
	    Configuration config = new Configuration();
	    ServletContext sc = request.getSession().getServletContext();
	    config.setDirectoryForTemplateLoading(new File(sc.getRealPath(TEMPLATE_PATH)));
	    config.setObjectWrapper(new DefaultObjectWrapper());
	    Template template = config.getTemplate(temp, "UTF-8");
        return template;
    }   

    /**
     * @param request
     * @param temp
     * @param root 
     */
    public static void toPreview(HttpServletRequest request, String temp, Map<?, ?> root){
        try {
	        String previewPath = request.getSession().getServletContext().getRealPath("")+PREVIEW_DOC;
		    Template template = configTemplate(request, temp);
		    FileOutputStream fos = new FileOutputStream(previewPath);
		    Writer out = new OutputStreamWriter(fos, "UTF-8");
	        template.process(root, out);
	        out.flush();
	        out.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }   
    } 
    /**
     * @param request
     * @param temp
     * @param root  
     */
    public static void toSafe(HttpServletRequest request, String temp, Map<?, ?> root){
        try {
	        String safePath = request.getSession().getServletContext().getRealPath("")+SAFE_DOC;
		    Template template = configTemplate(request, temp);
		    FileOutputStream fos = new FileOutputStream(safePath);
		    Writer out = new OutputStreamWriter(fos, "UTF-8");
	        template.process(root, out);
	        out.flush();
	        out.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }   
    } 
}
