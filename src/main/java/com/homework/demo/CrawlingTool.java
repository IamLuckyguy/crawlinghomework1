package com.homework.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CrawlingTool {
	public static void main(String[] args) {
		String URL = "https://search.shopping.naver.com/search/all?query=%EB%85%B8%ED%8A%B8%EB%B6%81&cat_id=&frm=NVSHATC";

		try {
			Document doc = Jsoup.connect(URL).get();//페이지 크롤링

			String selector = ".basicList_link__1MaTN";
			Elements pList = doc.select(selector);//셀렉터에 해당하는 클래스의 값을 가져옴.

			ArrayList<String> product = new ArrayList<>();
			ArrayList<String> price = new ArrayList<>();

			for(Element p : pList) {
				if ( p.attr("title") != "" ) {
					product.add(p.attr("title"));
				}
			}

			selector = ".basicList_mall_list__vIiQw";
			Elements mList = doc.select(selector);//셀렉터에 해당하는 클래스의 값을 가져옴.

			for(Element m : mList) {
				price.add(m.select(".basicList_price__2r23_").eq(0).text());//셀렉터에 해당하는 클래스 안에 text 값만 가져옴
			}


			int loopCount = product.size();

			String str  = "";//파일 출력을 위해 String 을 저장할 변수
			for(int i = 0; i < loopCount; i++) {
				System.out.println("상품이름 : " + product.get(i) + " / 상품가격 : " + price.get(i) + "원");
				str += "상품이름 : " + product.get(i) + " / 상품가격 : " + price.get(i) + "원 \n";
			}

			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");//로그 파일 이름에 사용될 날짜 유형 선언

			Date time = new Date();
			String fileName = format1.format(time);
			OutputStream output = new FileOutputStream(fileName+"최저가 리스트.log");

			byte[] by = str.getBytes();
			output.write(by);

		} catch ( Exception e ) {
			System.out.println("Exception Error!" + e);
		}

	}
}
