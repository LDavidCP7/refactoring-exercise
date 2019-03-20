package com.checkr.interviews;

import java.util.*;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class FundingRaised{

    public static List<String[]> getInfo(String key, int id, Map<String, String> options, List<String[]> csvData) {
        List<String[]> results = new ArrayList<String[]>();
        if (options.containsKey(key)) {
            for (int i = 0; i < csvData.size(); i++) {
                if (csvData.get(i)[id].equals(options.get(key))) {
                    results.add(csvData.get(i));
                }
            }
            csvData = results;
        }
        return csvData;
    }

    public static void mapeo(int i, List<String[]> csvData, Map<String, String> mapped) {
        mapped.put("permalink", csvData.get(i)[0]);
        mapped.put("company_name", csvData.get(i)[1]);
        mapped.put("number_employees", csvData.get(i)[2]);
        mapped.put("category", csvData.get(i)[3]);
        mapped.put("city", csvData.get(i)[4]);
        mapped.put("state", csvData.get(i)[5]);
        mapped.put("funded_date", csvData.get(i)[6]);
        mapped.put("raised_amount", csvData.get(i)[7]);
        mapped.put("raised_currency", csvData.get(i)[8]);
        mapped.put("round", csvData.get(i)[9]);
    }

    public static List<Map<String, String>> where(Map<String, String> options) throws IOException {
        List<String[]> csvData = new ArrayList<String[]>();
        CSVReader reader = new CSVReader(new FileReader("startup_funding.csv"));
        String[] row = null;

        while ((row = reader.readNext()) != null) {
            csvData.add(row);
        }

        reader.close();
        csvData.remove(0);

        csvData = getInfo("company_name", 1, options, csvData);
        csvData = getInfo("city", 4, options, csvData);
        csvData = getInfo("state", 5, options, csvData);
        csvData = getInfo("round", 9, options, csvData);

        List<Map<String, String>> output = new ArrayList<Map<String, String>>();

        for (int i = 0; i < csvData.size(); i++) {
            Map<String, String> mapped = new HashMap<String, String>();
            mapeo(i, csvData, mapped);
            output.add(mapped);
        }

        return output;
    }

    public static Map<String, String> findBy(Map<String, String> options) throws IOException, NoSuchEntryException {
        List<String[]> csvData = new ArrayList<String[]>();
        CSVReader reader = new CSVReader(new FileReader("startup_funding.csv"));
        String[] row = null;

        while ((row = reader.readNext()) != null) {
            csvData.add(row);
        }

        reader.close();
        csvData.remove(0);
        Map<String, String> mapped = new HashMap<String, String>();

        for (int i = 0; i < csvData.size(); i++) {
            if (options.containsKey("company_name")) {
                if (csvData.get(i)[1].equals(options.get("company_name"))) {
                    mapeo(i, csvData, mapped);
                } else {
                    continue;
                }
            }
            if (options.containsKey("city")) {
                if (csvData.get(i)[4].equals(options.get("city"))) {
                    mapeo(i, csvData, mapped);
                } else {
                    continue;
                }
            }
            if (options.containsKey("state")) {
                if (csvData.get(i)[5].equals(options.get("state"))) {
                    mapeo(i, csvData, mapped);
                } else {
                    continue;
                }
            }
            if (options.containsKey("round")) {
                if (csvData.get(i)[9].equals(options.get("round"))) {
                    mapeo(i, csvData, mapped);
                } else {
                    continue;
                }
            }
            return mapped;
        }
        throw new NoSuchEntryException();
    }

    public static void main(String[] args) {
        try {
            Map<String, String> options = new HashMap<String, String>();
            options.put("company_name", "Facebook");
            options.put("round", "a");
            System.out.print(FundingRaised.where(options).size());
        } catch (IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        }
    }
}

class NoSuchEntryException extends Exception {
}
