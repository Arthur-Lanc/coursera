
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
        records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
        FileResource fr = new FileResource(filename);
        for (String line : fr.lines()) {
            LogEntry le_temp = WebLogParser.parseEntry(line);
            records.add(le_temp);
        }
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }

     public int countUniqueIPs(){
        ArrayList<String> uniq_ip_list = new ArrayList<String>(); 
        for (LogEntry le : records) {
            String temp_ip_str = le.getIpAddress();
            if (!uniq_ip_list.contains(temp_ip_str)){
                uniq_ip_list.add(temp_ip_str);
            }
        }
        return uniq_ip_list.size();
     }
     
     public void printAllHigherThanNum (int num){
        for (LogEntry le : records) {
            int status_code = le.getStatusCode();
            if (status_code > num){
                System.out.println(le);
            }
        }
     }

     public ArrayList<String> uniqueIPVisitsOnDay(String someday ){
        ArrayList<String> uniq_ip_list = new ArrayList<String>(); 
        for (LogEntry le : records) {
            String temp_ip_str = le.getIpAddress();
            Date d = le.getAccessTime();
            String d_str = d.toString();
            if (d_str.contains(someday) && !uniq_ip_list.contains(temp_ip_str)){
                //System.out.println("le: "+le);
                uniq_ip_list.add(temp_ip_str);
            }
        }
        return uniq_ip_list;
     }

     public int countUniqueIPsInRange (int low,int high){
        ArrayList<String> uniq_ip_list = new ArrayList<String>(); 
        for (LogEntry le : records) {
            String temp_ip_str = le.getIpAddress();
            int status_code = le.getStatusCode();
            if(status_code >= low && status_code <= high && !uniq_ip_list.contains(temp_ip_str)){
                uniq_ip_list.add(temp_ip_str);
            }
        }
        return uniq_ip_list.size(); 
     }

     public HashMap<String, Integer> countVisitsPerIP(){
        HashMap<String, Integer> count_map = new HashMap<String, Integer>();
        for (LogEntry le : records) {
            String ip_str = le.getIpAddress();
            if (!count_map.containsKey(ip_str)){
                count_map.put(ip_str, 1);
            }
            else{
                count_map.put(ip_str, count_map.get(ip_str)+1);
            }
        }
        return count_map;
     }

     public int mostNumberVisitsByIP(HashMap<String, Integer> count_map){
        int max_visit_num = 0;
        for (Integer v : count_map.values()) {
            if (v > max_visit_num){
                max_visit_num = v;
            }
        }
        return max_visit_num;
     }

     public ArrayList<String> iPsMostVisits(HashMap<String, Integer> count_map){
        int max_visit_num = mostNumberVisitsByIP(count_map);
        ArrayList<String> ip_list = new ArrayList<String>();
        for (String k : count_map.keySet()) { 
            int v = count_map.get(k);
            if(v == max_visit_num){
                ip_list.add(k);
            }
        }
        return ip_list;
     }

     public HashMap<String, ArrayList<String>> iPsForDays(){
        HashMap<String, ArrayList<String>> result_map = new HashMap<String, ArrayList<String>>();
        for (LogEntry le : records) {
            String ip_str = le.getIpAddress();
            Date d = le.getAccessTime();
            String d_str = d.toString();
            String d_str2 = d_str.substring(4, 10);
            if(!result_map.containsKey(d_str2)){
                ArrayList<String> temp_list = new ArrayList<String>();
                temp_list.add(ip_str);
                result_map.put(d_str2, temp_list);
            }
            else{
                ArrayList<String> temp_list = result_map.get(d_str2);
                temp_list.add(ip_str);
                result_map.put(d_str2, temp_list);
            }
        }
        return result_map;
     }

     public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> day_iplist_map){
        int max_ipvisits_num = 0;
        String result_day_str = "";
        for (String day_str : day_iplist_map.keySet()) {
            ArrayList<String> ip_list = day_iplist_map.get(day_str);
            int ipvisits_num = ip_list.size();
            if (ipvisits_num > max_ipvisits_num){
                max_ipvisits_num = ipvisits_num;
                result_day_str = day_str;
            }
        } 
        return result_day_str;
     }

     public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> day_iplist_map,String day_str){
        ArrayList<String> ip_list = day_iplist_map.get(day_str);
        HashMap<String, Integer> ip_count_map= new HashMap<String, Integer>();
        for (String ip : ip_list) {
            if (!ip_count_map.containsKey(ip)){
                ip_count_map.put(ip,1);
            }
            else{
                ip_count_map.put(ip,ip_count_map.get(ip)+1);
            }
        }
        return iPsMostVisits(ip_count_map);
     }
}
