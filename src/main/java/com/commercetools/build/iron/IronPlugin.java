package com.commercetools.build.iron;

import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.asynchttpclient.request.body.multipart.FilePart;
import org.asynchttpclient.request.body.multipart.StringPart;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Mojo(name = "deploy")
public class IronPlugin extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}/${project.build.finalName}-jar-with-dependencies.jar")
    private String codeFile;
    //body settings
    @Parameter(property = "name", required = true, readonly = true)
    private String name;
    @Parameter(readonly = true, defaultValue = "iron/java")
    private String image;
    @Parameter
    private String command;
    @Parameter
    private String config;
    @Parameter
    private String maxConcurrency;
    @Parameter
    private String stack;
    @Parameter
    private Integer retries;
    @Parameter(defaultValue = "java")
    private String runtime;
    @Parameter
    private Integer retriesDelay;
    @Parameter
    private String defaultPriority;
    @Parameter
    private String envVars;
    //credentials
    @Parameter(property = "iron.token", readonly = true)
    private String token;
    @Parameter(property = "iron.projectId", readonly = true)
    private String projectId;

    public void execute() throws MojoExecutionException {
        try(final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient()) {
            final File file = new File(codeFile);
            final String data = getConfigPayloadString();
            final Response response = asyncHttpClient.preparePost(getCodeUploadUrl())
                    .addBodyPart(new StringPart("data", data))
                    .addBodyPart(new FilePart("file", file, "application/zip", null, file.getName()))
                    .execute().toCompletableFuture().join();
            if (!response.hasResponseStatus() || response.getStatusCode() > 201) {
                throw new RuntimeException("failed with " + response);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("failed", e);
        }
    }

    private String getConfigPayloadString() throws Exception {
        final CodesDraft codesDraft = new CodesDraft();
        BeanUtils.copyProperties(codesDraft, this);
        final Gson gson = new Gson();
        return gson.toJson(codesDraft);
    }

    private String getCodeUploadUrl() {
        final String format = "https://worker-aws-us-east-1.iron.io/2/projects/${projectId}/codes?oauth=${token}";
        final Map<String, Object> values = new HashMap<>();
        values.put("projectId", projectId);
        values.put("token", token);
        final String replace = StrSubstitutor.replace(format, values);
        return replace;
    }

    public String getCodeFile() {
        return codeFile;
    }

    public String getCommand() {
        return command;
    }

    public String getConfig() {
        return config;
    }

    public String getDefaultPriority() {
        return defaultPriority;
    }

    public String getEnvVars() {
        return envVars;
    }

    public String getImage() {
        return image;
    }

    public String getMaxConcurrency() {
        return maxConcurrency;
    }

    public String getName() {
        return name;
    }

    public String getProjectId() {
        return projectId;
    }

    public Integer getRetries() {
        return retries;
    }

    public Integer getRetriesDelay() {
        return retriesDelay;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getStack() {
        return stack;
    }

    public String getToken() {
        return token;
    }
}