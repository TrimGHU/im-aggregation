
package com.ubtech.im.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;

/**
 * @ClassName: HttpUtil
 * @Description: HTTP CLIENT类用于发送HTTP请求
 * @author: james.liao
 * @date: 2016年8月1日 上午11:12:03
 */
public class HttpUtil
{

    public static String sendGet( String url, String charset ) throws IOException
    {
        String result = null;
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        InputStream in = null;
        HttpGet httpGet = new HttpGet( url );
        httpGet.setHeader("device-info", "{}");
        httpGet.setHeader("common-info", "{}");
        try
        {
            // 执行get请求
            HttpResponse httpResponse = closeableHttpClient.execute( httpGet );
            // 获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            // 响应状态
            // 判断响应实体是否为空
            if ( entity != null )
            {
                entity = new BufferedHttpEntity( entity );
                in = entity.getContent();
                byte[] read = new byte[1024];
                byte[] all = new byte[0];
                int num;
                while ( (num = in.read( read )) > 0 )
                {
                    byte[] temp = new byte[all.length + num];
                    System.arraycopy( all, 0, temp, 0, all.length );
                    System.arraycopy( read, 0, temp, all.length, num );
                    all = temp;
                }
                result = new String( all, charset );
            }
        } finally
        {
            // 关闭流并释放资源
            closeableHttpClient.close();
            if ( in != null )
            {
                in.close();
            }
            httpGet.abort();
        }
        return result;
    }

    public String sendPost( String url, String xmlData, String charset ) throws ClientProtocolException, IOException
    {
        String result = null;
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost( url );

        StringEntity stringEntity = new StringEntity( xmlData );
        // 填充表单
        httpPost.setEntity( stringEntity );
        HttpResponse response = closeableHttpClient.execute( httpPost );
        HttpEntity entity = response.getEntity();
        if ( entity != null )
        {
            entity = new BufferedHttpEntity( entity );
            InputStream in = entity.getContent();
            byte[] read = new byte[1024];
            byte[] all = new byte[0];
            int num;
            while ( in != null && (num = in.read( read )) > 0 )
            {
                byte[] temp = new byte[all.length + num];
                System.arraycopy( all, 0, temp, 0, all.length );
                System.arraycopy( read, 0, temp, all.length, num );
                all = temp;
            }
            result = new String( all, charset );
            if ( null != in )
            {
                in.close();
            }
            // 关闭流并释放资源
            closeableHttpClient.close();
            httpPost.abort();
        }
        return result;
    }

    /**
     * http post方式 方法用途: <br>
     * 
     * @param url
     * @param params
     * @param charset
     *            字符集
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String sendPost( String url, Map<String, String> params, String charset )
            throws ClientProtocolException, IOException
    {
        String result = null;
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost( url );

        // 创建表单参数列表
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        for ( String key : keys )
        {
            qparams.add( new BasicNameValuePair( key, params.get( key ) ) );
        }
        
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity( qparams, charset );
        // 填充表单
        httpPost.setEntity(formEntity);

        HttpResponse response = closeableHttpClient.execute( httpPost );
        HttpEntity entity = response.getEntity();
        if ( entity != null )
        {
            entity = new BufferedHttpEntity( entity );
            InputStream in = entity.getContent();
            byte[] read = new byte[1024];
            byte[] all = new byte[0];
            int num;
            while ( in != null && (num = in.read( read )) > 0 )
            {
                byte[] temp = new byte[all.length + num];
                System.arraycopy( all, 0, temp, 0, all.length );
                System.arraycopy( read, 0, temp, all.length, num );
                all = temp;
            }
            result = new String( all, charset );
            if ( null != in )
            {
                in.close();
            }
            // 关闭流并释放资源
            closeableHttpClient.close();
            httpPost.abort();
        }
        return result;
    }
    

    public static String sendGet( String url, Map<String, String> params, String charset )
            throws ClientProtocolException, IOException
    {
        Set<String> keys = params.keySet();
        StringBuilder urlBuilder = new StringBuilder( url + "?" );
        for ( String key : keys )
        {
            urlBuilder.append( key ).append( "=" ).append( params.get( key ) ).append( "&" );
        }
        urlBuilder.delete( urlBuilder.length() - 1, urlBuilder.length() );
        return sendGet( urlBuilder.toString(), charset );
    }

    /**
     * https post方式 方法用途: <br>
     * 
     * @param url
     * @param params
     * @param charset
     *            字符集
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String sendHttpsPost( String url, Map<String, String> params, String charset ) throws Exception
    {
        String result = null;
        // HttpClient
        CloseableHttpClient closeableHttpClient = createSSLClientDefault();

        HttpPost httpPost = new HttpPost( url );

        // 创建表单参数列表
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        for ( String key : keys )
        {
            qparams.add( new BasicNameValuePair( key, params.get( key ) ) );
        }
        // 填充表单
        httpPost.setEntity( new UrlEncodedFormEntity( qparams, charset ) );

        HttpResponse response = closeableHttpClient.execute( httpPost );
        HttpEntity entity = response.getEntity();
        if ( entity != null )
        {
            entity = new BufferedHttpEntity( entity );
            InputStream in = entity.getContent();
            byte[] read = new byte[1024];
            byte[] all = new byte[0];
            int num;
            while ( in != null && (num = in.read( read )) > 0 )
            {
                byte[] temp = new byte[all.length + num];
                System.arraycopy( all, 0, temp, 0, all.length );
                System.arraycopy( read, 0, temp, all.length, num );
                all = temp;
            }
            result = new String( all, charset );
            if ( null != in )
            {
                in.close();
            }
            // 关闭流并释放资源
            closeableHttpClient.close();
            httpPost.abort();
        }
        return result;
    }

    public static CloseableHttpClient createSSLClientDefault() throws Exception
    {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial( null, new TrustStrategy()
        {
            // 信任所有
            public boolean isTrusted( X509Certificate[] chain, String authType ) throws CertificateException
            {
                return true;
            }
        } ).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory( sslContext );
        return HttpClients.custom().setSSLSocketFactory( sslsf ).build();
    }

}
