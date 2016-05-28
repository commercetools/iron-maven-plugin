package com.commercetools.build;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.concurrent.Future;

@Mojo( name = "sayhi")
public class IronPlugin extends AbstractMojo {


    @Parameter(required = true, readonly = true)
    private String name;
    @Parameter(required = true, readonly = true)
    private String image;
    @Parameter
    private String greeting;
    @Parameter(property = "iron.token", name = "iron.token", required = true, readonly = true)
    private String token;
    @Parameter(property = "iron.projectId", name = "iron.projectId", required = true, readonly = true)
    private String projectId;


    public void execute() throws MojoExecutionException {
        try(final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient()) {
            Future<Response> f = asyncHttpClient.prepareGet("http://commercetools.com/").execute();
            Response r = f.get();
            System.err.println(r);
        } catch (Exception e) {
            throw new MojoExecutionException("failed", e);
        }
        getLog().info( "Hello, world.3" );
        getLog().info(greeting);
        getLog().info(token);
        getLog().info(projectId);
    }
}