package com.gap.metrics.builder;

import com.gap.metrics.model.Iteration;
import com.gap.metrics.model.Project;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Repository;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportAlertMessageBuilder {
    private Project project;
    private Iteration iteration;
    private List<String> messages;

    public ReportAlertMessageBuilder with(Project project){
        this.project = project;
        return this;
    }

    public ReportAlertMessageBuilder with(Iteration iteration){
        this.iteration = iteration;
        return this;
    }

    public ReportAlertMessageBuilder with(List<String> messages){
        this.messages = messages;
        return this;
    }

    public String toString(){
        StringWriter output = new StringWriter();
        try
        {
            Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(this.getClass(), "/");
            Template template = cfg.getTemplate("mailtemplate.ftl");
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("project", project);
            model.put("iteration", iteration);
            model.put("messages", messages);
            template.process(model, output);
        }
        catch(Exception ex){
            String msg = ex.getMessage();
        }
        return output.toString();
    }
}
