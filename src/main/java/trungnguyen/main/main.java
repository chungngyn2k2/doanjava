package trungnguyen.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import trungnguyen.Lop;
import trungnguyen.SinhVienImpl;

public class main {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");;
	private Scanner input = new Scanner(System.in);
	private final String filePath = "./data.txt";
	
	public static void main(String[] args) {
		main t = new main();
		// Instance service
		int option = -1;
		
		Scanner sc = new Scanner(System.in);
		
		while (option != 0) {
			System.out.println("===============================================");
			System.out.println("||-------------QUAN LI SINH VIEN-------------||");
			System.out.println("|| 1. THEM SINH VIEN_________________________||");
			System.out.println("|| 2. XUAT DANH SACH SINH VIEN_______________||");
			System.out.println("|| 3. XUAT DANH SACH THEO LOP________________||");
			System.out.println("|| 4. SAP XEP THEO DIEM TONG KET TANG DAN____||"); 
			System.out.println("|| 5. SAP XAP THEO DIEM TONG KET GIAM DAN____||");
			System.out.println("|| 6. TIM KIEM SINH VIEN THEO TEN____________||");
			System.out.println("|| 7. TIM KIEM SINH VIEN THEO MSSV___________||");
			System.out.println("|| 8. XOA THONG TIN SINH VIEN THEO MSSV______||");
			System.out.println("|| 0. THOAT__________________________________||");
			System.out.println("===============================================");
			System.out.println("Hien tai co " + t.getNumberStudents() + " sinh vien.");
			System.out.println("Lua chon tac vu cua ban la (0-8): ");
			try {
				option = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
				System.out.println("------- LUA CHON PHAI LA SO -------");
			}
			
			// Chọn
			switch (option) {
				case 1:
					t.addStudents();
					break;
				case 2:
					t.printStudents();
					break;
				case 3:
					t.printsStudentsClass();
					break;
				case 4:
					t.ascendingPointSort();
					break;
				case 5:
					t.decreasePointSort();
					break;
				case 6:
					t.findByName();
					break;
				case 7:
					t.findByStdCode();
					break;
				case 8:
					t.removeByStdCode();
					break;
				default:
					break;
			}
		}
		
		System.out.println("\n\nDA THOAT CHUONG TRINH\n\n");
    }
	
	
	public int getNumberStudents() {
		return ReadFile(this.filePath).size();
	}

	
	public void printStudents() {
		String formatString = "%-5s | %-25s | %-10s | %-10s | %-25s | %-11s | %-6s | %-6s | %-6s | %-6s%n";
		String formatContentString = "%-58s %-77s %-2s%n";
	    String header = String.format(formatString, "MSV", "HO TEN", "LOP", "NGAY SINH", "DIA CHI", "SO DT", "DIEM 1", "DIEM 2", "DIEM 3", "TONG KET");
	    String content = String.format(formatContentString, "||", "DANH SACH SINH VIEN", "||");
	    
	    // In content + header
	    System.out.println("===========================================================================================================================================");
	    System.out.print(content);
	    System.out.println("===========================================================================================================================================");
	    System.out.println(header);
	    
	    // In danh sách sinh viên
	    for (SinhVienImpl sinhVien : ReadFile(filePath).values()) {
	    	System.out.println(sinhVien.toString());
		}
	}
	
	public void printStudents(List<SinhVienImpl> dsSinhVien) {
		String formatString = "%-5s | %-25s | %-10s | %-10s | %-25s | %-11s | %-6s | %-6s | %-6s | %-6s%n";
		String formatContentString = "%-58s %-77s %-2s%n";
	    String header = String.format(formatString, "MSV", "HO TEN", "LOP", "NGAY SINH", "DIA CHI", "SO DT", "DIEM 1", "DIEM 2", "DIEM 3", "TONG KET");
	    String content = String.format(formatContentString, "||", "DANH SACH SINH VIEN", "||");
	    
	    // In content + header
	    System.out.println("===========================================================================================================================================");
	    System.out.print(content);
	    System.out.println("===========================================================================================================================================");
	    System.out.println(header);
	    
	    // In danh sách sinh viên
	    for (SinhVienImpl sinhVien : dsSinhVien) {
	    	System.out.println(sinhVien.toString());
		}
	}
	
	
	public void printsStudentsClass() {
		// tạo 1 hash table chứ danh sách các lớp học.
		Hashtable<Lop, List<SinhVienImpl>> classes = new Hashtable<Lop, List<SinhVienImpl>>();
		
		// Sắp xếp sinh viên theo từng lớp.
		Lop lop = null;
		for (SinhVienImpl sv : ReadFile(filePath).values()) {
			// tạo object Lop
			lop = new Lop(sv.getMaLop(), sv.getTenLop(), sv.getNgayBatDau(), sv.getNgayKetThuc());
			
			// Kiểm tra xem đã tồn tại trong hashtable chưa
			// Nếu rồi thì thêm vào danh sách sinh viên
			if (classes.containsKey(lop)) {
				classes.get(lop).add(sv);
			}
			// Nếu chưa thì tạo cặp key và value mới
			else {
				List<SinhVienImpl> newList = new ArrayList<SinhVienImpl>();
				newList.add(sv);
				classes.put(lop, newList);
			}
		}
		
		// Định dạng chuỗi trước khi in,
		String formatString = "%-5s | %-25s | %-10s | %-10s | %-25s | %-11s | %-6s | %-6s | %-6s | %-6s%n";
		String formatContentString = "%-58s %-77s %-2s%n";
	    String header = String.format(formatString, "MSV", "HO TEN", "LOP", "NGAY SINH", "DIA CHI", "SO DT", "DIEM 1", "DIEM 2", "DIEM 3", "TONG KET");
	    String content = String.format(formatContentString, "||", "DANH SACH SINH VIEN", "||");
	    
	    // In content + header
	    System.out.println("===========================================================================================================================================");
	    System.out.print(content);
	    System.out.println("===========================================================================================================================================");
		
		// In danh sách ra theo từng lớp.
		Iterator<Lop> iterator = classes.keySet().iterator();
        while (iterator.hasNext()) {
        	// Lấy giá trị của key và value
            Lop key = iterator.next();
            List<SinhVienImpl> value = classes.get(key);
            
            // In ra Lớp
            String classContent = String.format(formatContentString, "||", key.getTenLop(), "||");
            System.out.println("===========================================================================================================================================");
    	    System.out.print(classContent);
    	    System.out.println("===========================================================================================================================================");
    	    System.out.println(header);
            
    	    // In danh sách sinh viên của mỗi lớp.
    	    for (SinhVienImpl sinhVien : value) {
    	    	System.out.println(sinhVien.toString());
    		}
        }
	}

	
	public void ascendingPointSort() {
		List<SinhVienImpl> dsSinhVien = new ArrayList<>(ReadFile(filePath).values());

		// selection sort
		SinhVienImpl svTemp = null;
		for (int i = 0; i < dsSinhVien.size(); i++) {
			for (int j = i+1; j < dsSinhVien.size(); j++) {
				if (dsSinhVien.get(j).diemTongKet() < dsSinhVien.get(i).diemTongKet()) {
					// swap
					svTemp = dsSinhVien.get(j);
					dsSinhVien.set(j, dsSinhVien.get(i));
					dsSinhVien.set(i, svTemp);
				}
			}
		}
		
		// In danh sách
		this.printStudents(dsSinhVien);
	}

	
	public void decreasePointSort() {
		List<SinhVienImpl> dsSinhVien = new ArrayList<>(ReadFile(filePath).values());

		// selection sort
		SinhVienImpl svTemp = null;
		for (int i = 0; i < dsSinhVien.size(); i++) {
			for (int j = i+1; j < dsSinhVien.size(); j++) {
				if (dsSinhVien.get(j).diemTongKet() > dsSinhVien.get(i).diemTongKet()) {
					svTemp = dsSinhVien.get(j);
					dsSinhVien.set(j, dsSinhVien.get(i));
					dsSinhVien.set(i, svTemp);
				}
			}
		}
		
		// In danh sách
		this.printStudents(dsSinhVien);
	}

	
	public void findByName() {
		System.out.println("NHAP TEN SINH VIEN CAN TIM: ");
		String findContent = input.nextLine();
		
		List<SinhVienImpl> dsSinhVien = new ArrayList<>(ReadFile(filePath).values());

        // Tìm kiếm các tên gần giống
        List<SinhVienImpl> resultSreach = new ArrayList<SinhVienImpl>();
        for (SinhVienImpl sv : dsSinhVien) {
        	
        	String name = sv.getHoTen().toLowerCase();
        	
            if (name.contains(findContent.toLowerCase())) {
            	resultSreach.add(sv);
            }
        }
        
        // In danh sách
        this.printStudents(resultSreach);
	}

	
	public void findByStdCode() {
		System.out.println("NHAP MSSV CAN TIM: ");
		
		// Tìm theo Key của hashset
		List<SinhVienImpl> dsSinhVien = null;
		try {
			SinhVienImpl sv = ReadFile(filePath).get(Integer.parseInt(input.nextLine()));
			
			dsSinhVien = new ArrayList<SinhVienImpl>();
			dsSinhVien.add(sv);
			
		} catch (Exception e) {
			System.out.println("----MSSV phai la so!----");
		}
		
		// In danh sách
        this.printStudents(dsSinhVien);
	}

	
	public void removeByStdCode() {
		System.out.println("NHAP MSSV CAN XOA: ");
		Hashtable<Integer, SinhVienImpl> newData = ReadFile(filePath);
		try {
			newData.remove(Integer.parseInt(input.nextLine()));
		} catch (Exception e) {
			System.out.println("----MSSV phai la so!----");
		}
		
		// Tạo dữ liệu
		String content = "";
		
		for (SinhVienImpl sv : newData.values()) {
			content += sv.getMSV() + "," + sv.getHoTen() + "," + dateFormat.format(sv.getNgaySinh())  + "," +  sv.getDiaChi()
			 + "," +  sv.getSoDt() + "," + sv.getDiem1() + "," + sv.getDiem2() + "," + sv.getDiem3()
			 + "," + sv.getMaLop() + "," + sv.getTenLop() + "," + dateFormat.format(sv.getNgayBatDau()) + "," + dateFormat.format(sv.getNgayKetThuc()) + "///";
		}
		
		// Lưu dữ liệu
		WriteFile(filePath, content);
		
		// In dữ liêu
		this.printStudents();
	}

	
	public void addStudents() {
		String content = "";
		
		// Tạo dữ liệu
		for (SinhVienImpl sv : ReadFile(filePath).values()) {
			content += sv.getMSV() + "," + sv.getHoTen() + "," + dateFormat.format(sv.getNgaySinh())  + "," +  sv.getDiaChi()
			 + "," +  sv.getSoDt() + "," + sv.getDiem1() + "," + sv.getDiem2() + "," + sv.getDiem3()
			 + "," + sv.getMaLop() + "," + sv.getTenLop() + "," + dateFormat.format(sv.getNgayBatDau()) + "," + dateFormat.format(sv.getNgayKetThuc()) + "///";
		}
		
		// Thêm dữ liệu
		System.out.println("MA SINH VIEN: " + (getNumberStudents()+1));
		content += (getNumberStudents()+1) + ",";
		System.out.println("NHAP TEN SINH VIEN: ");
		content += input.nextLine() + ",";
		System.out.println("NHAP NGAY SINH (NGAY/THANG/NAM): ");
		content += input.nextLine() + ",";
		System.out.println("NHAP DIA CHI: ");
		content += input.nextLine() + ",";
		System.out.println("NHAP SO DIEN THOAI: ");
		content += input.nextLine() + ",";
		System.out.println("NHAP DIEM 1: ");
		content += input.nextLine() + ",";
		System.out.println("NHAP DIEM 2: ");
		content += input.nextLine() + ",";
		System.out.println("NHAP DIEM 3: ");
		content += input.nextLine() + ",";
		System.out.println("NHAP MA LOP: ");
		content += input.nextLine() + ",";
		System.out.println("NHAP TEN LOP: ");
		content += input.nextLine() + ",";
		System.out.println("NHAP NGAY BAT DAU (NGAY/THANG/NAM): ");
		content += input.nextLine() + ",";
		System.out.println("NHAP KET THUC (NGAY/THANG/NAM): ");
		content += input.nextLine();
		
		// Lưu dữ liệu
		WriteFile(filePath, content);
	}
	
	public void WriteFile(String filePath, String content) {
		// Lưu dữ liệu vào txt file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			// Lưu từng dòng dữ liệu vào file text
			for (String line : content.split("///")) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public Hashtable<Integer, SinhVienImpl> ReadFile(String filePath) {
		// Tạo 1 hashtable lưu trữ danh sách sinh viên đọc được
		Hashtable<Integer, SinhVienImpl> data = new Hashtable<Integer, SinhVienImpl>();
		
		// Định dạng dữ liệu ngày tháng
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] content;
            SinhVienImpl sinhVien = null;
            
            // Đọc dữ liệu của từng dòng và lưu vào hashtable
            while ((line = reader.readLine()) != null) {
            	content = line.split(",");
                sinhVien = new SinhVienImpl(Integer.parseInt(content[0]), content[1], dateFormat.parse(content[2]), content[3], content[4], Double.parseDouble(content[5]), Double.parseDouble(content[6]), Double.parseDouble(content[7]), Integer.parseInt(content[8]), content[9], dateFormat.parse(content[10]), dateFormat.parse(content[11]));
                
                // Lưu Dữ liệu
                data.put(Integer.parseInt(content[0]), sinhVien);
            }
        } catch (IOException | NumberFormatException | ParseException e) {
            e.printStackTrace();
        }
        return data;
	}
}
