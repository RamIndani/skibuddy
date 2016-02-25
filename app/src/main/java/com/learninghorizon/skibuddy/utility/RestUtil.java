package com.learninghorizon.skibuddy.utility;

import android.util.Log;

import com.learninghorizon.skibuddy.model.SkiEvent;
import com.learninghorizon.skibuddy.model.SkiSession;
import com.learninghorizon.skibuddy.model.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ramnivasindani on 11/27/15.
 */
public class RestUtil {

    private static final String TAG = "RestUtil";
    private static RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }
    public static User createUser(User user, final String url){
        ResponseEntity<User>  response = null;
        try {
            /*RestTemplate restTemplate = getRestTemplate();
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            map.add("user", user.getFirstName());
            map.add("lastName", user.getLastName());
            map.add("facebookId", user.getFacebookId());
            map.add("tagLine", "Your tag line here");
             response = restTemplate.postForObject(url, map, User.class,httpHeaders);
            Log.e(TAG, "Response of create user: " + response.getFirstName());
*/
            MultiValueMap<String, User> map = new LinkedMultiValueMap<String, User>();
            map.add("user", user);
            HttpHeaders requestHeaders=new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<User> requestEntity=new HttpEntity<User>(user);
            RestTemplate restTemplate=getRestTemplate();
            response=restTemplate.exchange(url, HttpMethod.POST,requestEntity,User.class);
            //return response.getBody();
        }catch(Exception exception){
            Log.e(TAG, "Error:");
            exception.printStackTrace();
        }
        if(null != response) {
            return response.getBody();
        }else{
            return null;
        }
    }

    public static void updateTagLine(String tagLine, final String url){
        try{
            RestTemplate restTemplate = getRestTemplate();
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            map.add("tagLine", tagLine);
            User response = restTemplate.postForObject(url, map, User.class, httpHeaders);
            //Log.e(TAG, "Response of create user: " + response.getFirstName());
        }catch(Exception exception){
            Log.e("TAG", exception.getLocalizedMessage());
            exception.printStackTrace();
        }
    }


    public static User loadUserProfile(String url) {
        User response = null;
        try{
            RestTemplate restTemplate = getRestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
             response = restTemplate.getForObject(url, User.class, httpHeaders);
            Log.e(TAG, "Response of create user: " + response.getFirstName());
        }catch(Exception exception){
            Log.e("TAG", exception.getLocalizedMessage());
            exception.printStackTrace();
        }
        return response;
    }

    public static SkiSession saveSession(SkiSession skiSession, final String url) {
        ResponseEntity<SkiSession>  response = null;
        try {
            /*RestTemplate restTemplate = getRestTemplate();
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            map.add("user", user.getFirstName());
            map.add("lastName", user.getLastName());
            map.add("facebookId", user.getFacebookId());
            map.add("tagLine", "Your tag line here");
             response = restTemplate.postForObject(url, map, User.class,httpHeaders);
            Log.e(TAG, "Response of create user: " + response.getFirstName());
*/
            MultiValueMap<String, SkiSession> map = new LinkedMultiValueMap<String, SkiSession>();
            map.add("skiSession", skiSession);
            HttpHeaders requestHeaders=new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<SkiSession> requestEntity=new HttpEntity<SkiSession>(skiSession);
            RestTemplate restTemplate=getRestTemplate();
            response=restTemplate.exchange(url, HttpMethod.PUT,requestEntity,SkiSession.class);
            //return response.getBody();
        }catch(Exception exception){
            Log.e(TAG, "Error:");
            exception.printStackTrace();
        }
        Log.e(TAG, response.getBody().toString());
        return response.getBody();
    }


    public static List<User> listUsers(final String url) {
        User[] response = null;
        try {
            //MultiValueMap<String, SkiSession> map = new LinkedMultiValueMap<String, SkiSession>();
            //map.add("skiSession", skiSession);
            HttpHeaders requestHeaders=new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
           // HttpEntity<SkiSession> requestEntity=new HttpEntity<SkiSession>(skiSession);
            RestTemplate restTemplate=getRestTemplate();
            response=restTemplate.getForObject(url, User[].class);
            //return response.getBody();
        }catch(Exception exception){
            Log.e(TAG, "Error:");
            exception.printStackTrace();
        }
        Log.e(TAG, Arrays.asList(response).toString());
        return Arrays.asList(response);
    }


    public static void createEvent(SkiEvent skiEvent, String url) {
        ResponseEntity<SkiEvent>  response = null;
        try{
        MultiValueMap<String, SkiEvent> map = new LinkedMultiValueMap<String, SkiEvent>();
        map.add("skiEvent", skiEvent);
        HttpHeaders requestHeaders=new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<SkiEvent> requestEntity=new HttpEntity<SkiEvent>(skiEvent);
        RestTemplate restTemplate=getRestTemplate();
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SkiEvent.class);
        //return response.getBody();
    }catch(Exception exception) {
            Log.e(TAG, "Error:");
            exception.printStackTrace();
        }
    }

    public static List<User> loadUsers(List<String> eventInvites, String url) {
        User[]  response = null;
        try {
            /*RestTemplate restTemplate = getRestTemplate();
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            map.add("user", user.getFirstName());
            map.add("lastName", user.getLastName());
            map.add("facebookId", user.getFacebookId());
            map.add("tagLine", "Your tag line here");
             response = restTemplate.postForObject(url, map, User.class,httpHeaders);
            Log.e(TAG, "Response of create user: " + response.getFirstName());
*/
            MultiValueMap<String, List<String>> map = new LinkedMultiValueMap<String, List<String>>();
            map.add("userIds", eventInvites);
            HttpHeaders requestHeaders=new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<List<String>> requestEntity=new HttpEntity<List<String>>(eventInvites);
            RestTemplate restTemplate=getRestTemplate();
            response=restTemplate.postForObject(url, requestEntity, User[].class);
            //return response.getBody();
        }catch(Exception exception){
            Log.e(TAG, "Error:");
            exception.printStackTrace();
        }
        if(null != response) {
            return Arrays.asList(response);
        }else{
            return null;
        }
    }

    public static void shareLocation(String url) {
        Void response = null;
        try {
            //MultiValueMap<String, SkiSession> map = new LinkedMultiValueMap<String, SkiSession>();
            //map.add("skiSession", skiSession);
            HttpHeaders requestHeaders=new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            // HttpEntity<SkiSession> requestEntity=new HttpEntity<SkiSession>(skiSession);
            RestTemplate restTemplate=getRestTemplate();
            restTemplate.getForEntity(url,null, String.class);
            //return response.getBody();
        }catch(Exception exception){
            Log.e(TAG, "Error:");
            exception.printStackTrace();
        }
        Log.e(TAG, Arrays.asList(response).toString());
        //return Arrays.asList(response);
    }

    public static void saveLocation(String url) {
        Void response = null;
        try {
            Log.e("RESTUTIL ", url);
            //MultiValueMap<String, SkiSession> map = new LinkedMultiValueMap<String, SkiSession>();
            //map.add("skiSession", skiSession);
            HttpHeaders requestHeaders=new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            // HttpEntity<SkiSession> requestEntity=new HttpEntity<SkiSession>(skiSession);
            RestTemplate restTemplate=getRestTemplate();
            restTemplate.getForEntity(url,null, String.class);
            //return response.getBody();

        }catch(Exception exception){
            Log.e(TAG, "Error:");
            exception.printStackTrace();
        }
        //Log.e(TAG, Arrays.asList(response).toString());
    }
}
