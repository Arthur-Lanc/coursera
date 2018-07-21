
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        la.printAll();
    }
    
    public void testUniqueIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        int result = la.countUniqueIPs();
        System.out.println("unique ip: "+result);
    }

    public void testPrintAllHigherThanNum (){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        la.printAllHigherThanNum(400);
    }

    public void testUniqueIPVisitsOnDay (){
        ArrayList<String> result;
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        //result = la.uniqueIPVisitsOnDay("Sep 14");
        //System.out.println("result: "+result.size());
        result = la.uniqueIPVisitsOnDay("Sep 24");
        System.out.println("result: "+result.size());
    }

    public void testCountUniqueIPsInRange (){
        int result;
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        result = la.countUniqueIPsInRange(400,499);
        System.out.println("result: "+result);
        //result = la.countUniqueIPsInRange(300,399);
        //System.out.println("result: "+result);
    }

    public void testCountVisitsPerIP (){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        HashMap<String, Integer> count_map = la.countVisitsPerIP();
        System.out.println(count_map);
    }

    public void testMostNumberVisitsByIP (){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, Integer> count_map = la.countVisitsPerIP();
        int max_visit_num = la.mostNumberVisitsByIP(count_map);
        System.out.println("max_visit_num: "+max_visit_num);
    }

    public void testIPsMostVisits (){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, Integer> count_map = la.countVisitsPerIP();
        ArrayList<String> ip_list = la.iPsMostVisits(count_map);
        System.out.println("ip_list: "+ip_list);
    }
    
    public void testIPsForDays (){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        HashMap<String, ArrayList<String>> result_map = la.iPsForDays();
        System.out.println(result_map);
    }

    public void testDayWithMostIPVisits (){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> day_iplist_map = la.iPsForDays();
        String result_day_str = la.dayWithMostIPVisits(day_iplist_map);
        System.out.println(result_day_str);
    }

    public void testIPsWithMostVisitsOnDay (){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> day_iplist_map = la.iPsForDays();
        ArrayList<String> mostvisitsonday_ip_list = la.iPsWithMostVisitsOnDay(day_iplist_map,"Sep 30");
        System.out.println(mostvisitsonday_ip_list);
    }
}
