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
		System.out.print("������ ���� ���� �Է�: ");
		int num=in.nextInt();
		SERVER_INFO sinfo[]=new SERVER_INFO[num];		//���� ����
		for(int i=0;i<num;i++) {
			sinfo[i]=new SERVER_INFO(); 
		}
		
		int menu=0;
		
		do {
		System.out.println("1.����� ��û �Է�, 2.���� ���� ���, 3.���α׷� ����");
		System.out.print("�޴� ����: ");
		menu=in.nextInt();
		
		
		if(menu==1) {
			System.out.print("ip �Է��ϼ���: ");
			int ip=in.nextInt();
			System.out.print("���� �Է��ϼ���: ");
			String content=bf.readLine();
			System.out.print("���� �ð�: ");
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
			System.out.print("ip �Է��ϼ���: ");
			int ipx=in.nextInt();
			
			for(int i=0;i<sinfo.length;i++) {
				int chk=sinfo[i].getIP(ipx);

				if(chk==ipx) {
					System.out.println("\n---------------------------------------");
					System.out.println("������ȣ\t\t "+i);
					sinfo[i].printinfo(chk);
				}
			}
		}
		
		
		}while(menu!=3);
		System.out.println("����Ǿ����ϴ�.");
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
			System.out.println("�뷮�� �����մϴ�. ���� ���� ��¸� �����մϴ�.");
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
	private int ip[]=new int[4];				//ip�ּ�
	private String content[]=new String[4];		//��û����
	private String time[]=new String[4];		//��û�� ���� �ð�
	
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
		System.out.println("cpu��뷮\t\t "+cpu+"%");
		System.out.println("memory��뷮\t "+mem+"MB");
		System.out.println("HDD��뷮\t\t "+HDD+"GB");
		System.out.println("Network��뷮\t "+net+"%");
		System.out.println("ip adress\t "+ip[i]);
		System.out.println("content\t\t "+content[i]);
		System.out.println("stored time\t "+time[i]);
		System.out.println("---------------------------------------\n");
	}
}

class USER_REQUEST{
	private int ip;				//ip�ּ�
	private String content;		//��û����
	private String time;		//��û�� ���� �ð�
	
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
