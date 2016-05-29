package com.commercetools.build.iron;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean2;
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
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Mojo( name = "sayhi")
public class IronPlugin extends AbstractMojo {

    //body settings
    @Parameter(required = true, readonly = true)
    private String name;
    @Parameter(required = true, readonly = true)
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
    @Parameter
    private Integer retriesDelay;
    @Parameter
    private String defaultPriority;
    @Parameter
    private String envVars;
    //credentials
    @Parameter(property = "iron.token", name = "iron.token", required = true, readonly = true)
    private String token;
    @Parameter(property = "iron.projectId", name = "iron.projectId", required = true, readonly = true)
    private String projectId;

    public void execute() throws MojoExecutionException {
        try(final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient()) {
            final Response response = asyncHttpClient.preparePost(getCodeUploadUrl())
                    .addBodyPart(new StringPart("settings", getConfigPayloadString()))
                    .addBodyPart(new FilePart("code", new File(".gitignore")))
                    .execute().toCompletableFuture().join();
            System.err.println(response);
        } catch (Exception e) {
            throw new MojoExecutionException("failed", e);
        }
    }

    private String getConfigPayloadString() throws IllegalAccessException, InvocationTargetException {
        final CodesDraft codesDraft = new CodesDraft();
        BeanUtils.copyProperties(codesDraft, this);
        final Gson gson = new Gson();
        return gson.toJson(codesDraft);
    }

    private String getCodeUploadUrl() {
        final String format = "https://worker-aws-us-east-1.iron.io/2/projects/${projectID}/codes?oauth=${token}";
        final Map<String, Object> values = new HashMap<>();
        values.put("projectId", projectId);
        values.put("token", token);
        final String replace = StrSubstitutor.replace(format, values);
        return replace;
    }
}