import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
public class LB_main {
	static int pc=0;
	
	public static void main(String[] args) throws IOException {
		Scanner in=new Scanner(System.in);
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		System.out.print("구동할 서버 개수 입력: ");
		int num=in.nextInt();
		SERVER_INFO sinfo[]=new SERVER_INFO[num];		//서버 생성
		for(int i=0;i<num;i++) {
			sinfo[i]=new SERVER_INFO(); 
		}
		
		int menu=0;
		
		do {
		System.out.println("1.사용자 요청 입력, 2.서버 정보 출력, 3.프로그램 종료");
		System.out.print("메뉴 선택: ");
		menu=in.nextInt();
		
		
		if(menu==1) {
			System.out.print("ip 입력하세요: ");
			int ip=in.nextInt();
			System.out.print("내용 입력하세요: ");
			String content=bf.readLine();
			System.out.print("현재 시간: ");
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			Date t = new Date();
			String time=format1.format(t);
			System.out.println(time+"\n");
			
			USER_REQUEST req=new USER_REQUEST(ip, content, time);
	
			int lb=LB(req, sinfo);
			if(lb==-1) continue;
			
			sinfo[lb].SetAll(req);
		}
		
		else if(menu ==2){
			System.out.print("ip 입력하세요: ");
			int ipx=in.nextInt();
			
			for(int i=0;i<sinfo.length;i++) {
				int chk=sinfo[i].getIP(ipx);

				if(chk==ipx) {
					System.out.println("\n---------------------------------------");
					System.out.println("서버번호\t\t "+i);
					sinfo[i].printinfo(chk);
				}
			}
		}
		
		
		}while(menu!=3);
		System.out.println("종료되었습니다.");
	}
	
	public static int LB(USER_REQUEST req, SERVER_INFO[] sinfo) {

		if(pc >= sinfo.length) pc=0;
		
		try {
			while(sinfo[pc].getCpuUsage() == 100) {
				if(pc >= sinfo.length) pc=0;
				pc++;
			}
			
			while(sinfo[pc].getMemUsage() == 1000) {
				if(pc >= sinfo.length) pc=0;
				pc++;
			}
			
			while(sinfo[pc].getHDD() == 100) {
				if(pc >= sinfo.length) pc=0;
				pc++;
			}
			
			while(sinfo[pc].getNet() == 100) {
				if(pc >= sinfo.length) pc=0;
				pc++;
			}
		} catch (Exception e) {
			System.out.println("용량이 부족합니다. 서버 정보 출력만 가능합니다.");
			return -1;
		}

		return pc++;
	}

}


class SERVER_INFO{

	private int num=0;
	private int cpu=0;
	private int mem=0;
	private int HDD=20;
	private int net=0;
	private int ip[]=new int[4];				//ip주소
	private String content[]=new String[4];		//요청내용
	private String time[]=new String[4];		//요청을 보낸 시간
	
	public SERVER_INFO() {
		
	}

	public void SetAll(USER_REQUEST req) {
		try {
			this.ip[num] = req.getIP();
			this.content[num] = req.getContent();
			this.time[num] = req.getTime();
		
		num++;
		cpu+=10;
		mem+=100;
		HDD+=1;
		net+=5;
		} catch (Exception e) {
			
		}
	}
	
	public int getCpuUsage() {
		return cpu;
	}
	public int getMemUsage() {
		return mem;
	}
	public int getHDD() {
		return HDD;
	}
	public int getNet() {
		return net;
	}
	private int i=0;
	public int getIP(int n) {
		for(i=0;i<4;i++) {
			if(ip[i]==n)
				return ip[i];
		}
		return 0;
	}
	
	public void printinfo(int n) {
		System.out.println("cpu사용량\t\t "+cpu+"%");
		System.out.println("memory사용량\t "+mem+"MB");
		System.out.println("HDD사용량\t\t "+HDD+"GB");
		System.out.println("Network사용량\t "+net+"%");
		System.out.println("ip adress\t "+ip[i]);
		System.out.println("content\t\t "+content[i]);
		System.out.println("stored time\t "+time[i]);
		System.out.println("---------------------------------------\n");
	}
}

class USER_REQUEST{
	private int ip;				//ip주소
	private String content;		//요청내용
	private String time;		//요청을 보낸 시간
	
	public USER_REQUEST(int ip, String content, String time){
		this.ip=ip;
		this.content=content;
		this.time=time;
	}
	
	public int getIP() {
		return ip;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getTime() {
		return time;
	}
}
