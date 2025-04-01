-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS quan_ly_hoc_vien;
USE quan_ly_hoc_vien;

-- Cấu trúc bảng cho bảng `hoc_vien`
CREATE TABLE `hoc_vien` (
  `ma_hoc_vien` int(11) NOT NULL,
  `ho_ten` varchar(255) DEFAULT NULL,
  `ngay_sinh` date DEFAULT NULL,
  `gioi_tinh` bit(1) NOT NULL,
  `so_dien_thoai` varchar(255) DEFAULT NULL,
  `dia_chi` varchar(255) DEFAULT NULL,
  `tinh_trang` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Đang đổ dữ liệu cho bảng `hoc_vien`
INSERT INTO `hoc_vien` (`ma_hoc_vien`, `ho_ten`, `ngay_sinh`, `gioi_tinh`, `so_dien_thoai`, `dia_chi`, `tinh_trang`) VALUES
(5, 'Van Tu Nguyen', '2003-03-01', b'1', '0968146590', 'Ho Chi Minh City ', b'1'),
(6, 'Đỗ Văn Ri', '1985-06-17', b'1', '0123456789', 'Portugal', b'1'),
(7, 'Adam Nguyen', '2003-03-01', b'1', '0968146590', 'Viet Tri', b'1'),
(8, 'Văn Đại', '1993-06-28', b'1', NULL, 'Netherlands', b'1'),
(9, 'Nguyễn Thị Dứa', '2020-08-03', b'0', NULL, 'Vinh Phuc', b'1'),
(10, 'Mã Tiến An', '1994-04-28', b'1', NULL, 'England', b'1'),
(11, 'Huỳnh Như', '2000-03-28', b'0', NULL, 'VietNam', b'1');

-- Cấu trúc bảng cho bảng `khoa_hoc`
CREATE TABLE `khoa_hoc` (
  `ma_khoa_hoc` int(11) NOT NULL,
  `ten_khoa_hoc` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(255) DEFAULT NULL,
  `ngay_bat_dau` date DEFAULT NULL,
  `ngay_ket_thuc` date DEFAULT NULL,
  `tinh_trang` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Đang đổ dữ liệu cho bảng `khoa_hoc`
INSERT INTO `khoa_hoc` (`ma_khoa_hoc`, `ten_khoa_hoc`, `mo_ta`, `ngay_bat_dau`, `ngay_ket_thuc`, `tinh_trang`) VALUES
(3, 'JAVA SWING', 'Đào tạo cơ bản về lập trình giao diện Java Swing', '2024-01-01', '2024-12-01', b'1'),
(4, 'Data Structrure and Algorithm', 'Cung cấp kiến thức về thuật toán và cấu trúc dữ liệu cho lập trình viên', '2024-01-01', '2024-12-01', b'1'),
(5, 'HTML/CSS nâng cao', 'Đào tạo nâng cao HTML/CSS cho lập trình viên', '2024-03-01', '2024-12-01', b'1'),
(1001, 'Java Spring And React JS', 'Khóa học dành cho lập trình viên Java FullStack', '2024-05-01', '2024-12-01', b'1'),
(1002, 'Lập Trình Web Với JPS Servlet', 'Thành thạo công nghệ JSP Servlet', '2024-05-01', '2024-07-30', b'1');

-- Cấu trúc bảng cho bảng `lop_hoc`
CREATE TABLE `lop_hoc` (
  `ma_lop_hoc` int(11) NOT NULL,
  `ma_khoa_hoc` int(11) DEFAULT NULL,
  `ma_hoc_vien` int(11) DEFAULT NULL,
  `ngay_dang_ky` date DEFAULT NULL,
  `tinh_trang` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Đang đổ dữ liệu cho bảng `lop_hoc`
INSERT INTO `lop_hoc` (`ma_lop_hoc`, `ma_khoa_hoc`, `ma_hoc_vien`, `ngay_dang_ky`, `tinh_trang`) VALUES
(12, 3, 6, '2024-01-08', b'1'),
(13, 5, 7, '2024-03-28', b'1'),
(14, 4, 11, '2024-06-28', b'1'),
(15, 3, 8, '2024-06-28', b'1'),
(16, 5, 5, '2024-01-03', b'1'),
(17, 4, 9, '2024-06-28', b'1'),
(18, 5, 9, '2024-01-08', b'1'),
(19, 1001, 5, '2024-01-03', b'1');

-- Cấu trúc bảng cho bảng `tai_khoan`
CREATE TABLE `tai_khoan` (
  `ma_tai_khoan` int(11) NOT NULL,
  `ten_dang_nhap` varchar(50) NOT NULL,
  `mat_khau` varchar(50) NOT NULL,
  `tinh_trang` bit(1) NOT NULL DEFAULT b'1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Chỉ mục cho các bảng đã đổ
ALTER TABLE `hoc_vien`
  ADD PRIMARY KEY (`ma_hoc_vien`);

ALTER TABLE `khoa_hoc`
  ADD PRIMARY KEY (`ma_khoa_hoc`);

ALTER TABLE `lop_hoc`
  ADD PRIMARY KEY (`ma_lop_hoc`),
  ADD KEY `FK_nsk96k2rdocji4oakgsv51nuq` (`ma_hoc_vien`),
  ADD KEY `FK_pvhxvf4oy1n5bp8tvn8a6tcnu` (`ma_khoa_hoc`);

ALTER TABLE `tai_khoan`
  ADD PRIMARY KEY (`ma_tai_khoan`);

-- AUTO_INCREMENT cho các bảng đã đổ
ALTER TABLE `hoc_vien`
  MODIFY `ma_hoc_vien` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

ALTER TABLE `khoa_hoc`
  MODIFY `ma_khoa_hoc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1003;

ALTER TABLE `lop_hoc`
  MODIFY `ma_lop_hoc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

ALTER TABLE `tai_khoan`
  MODIFY `ma_tai_khoan` int(11) NOT NULL AUTO_INCREMENT;

-- Các ràng buộc cho các bảng đã đổ
ALTER TABLE `lop_hoc`
  ADD CONSTRAINT `FK_nsk96k2rdocji4oakgsv51nuq` FOREIGN KEY (`ma_hoc_vien`) REFERENCES `hoc_vien` (`ma_hoc_vien`),
  ADD CONSTRAINT `FK_pvhxvf4oy1n5bp8tvn8a6tcnu` FOREIGN KEY (`ma_khoa_hoc`) REFERENCES `khoa_hoc` (`ma_khoa_hoc`);
	
ALTER TABLE lop_hoc
	
ADD ten_lop_hoc VARCHAR(255);

INSERT INTO hoc_vien (ho_ten, ngay_sinh, gioi_tinh, so_dien_thoai, dia_chi, tinh_trang) VALUES
('Nguyễn Văn A', '2002-05-15', b'1', '0901234567', 'Hà Nội', b'1'),
('Trần Thị B', '2000-09-21', b'0', '0912345678', 'TP. HCM', b'1'),
('Phạm Văn C', '2001-07-10', b'1', '0987654321', 'Đà Nẵng', b'1'),
('Lê Thị D', '1999-03-18', b'0', '0976543210', 'Cần Thơ', b'1'),
('Bùi Văn E', '2003-12-05', b'1', '0965432109', 'Hải Phòng', b'1'),
('Đỗ Thị F', '1998-11-30', b'0', '0954321098', 'Huế', b'1'),
('Ngô Văn G', '2002-06-25', b'1', '0943210987', 'Vinh', b'1'),
('Đặng Thị H', '1997-02-14', b'0', '0932109876', 'Quảng Ninh', b'1'),
('Trịnh Văn I', '2004-08-20', b'1', '0921098765', 'Bắc Ninh', b'1'),
('Phan Thị K', '2001-10-11', b'0', '0910987654', 'Thanh Hóa', b'1');

INSERT INTO khoa_hoc (ten_khoa_hoc, mo_ta, ngay_bat_dau, ngay_ket_thuc, tinh_trang) VALUES
('Lập trình C++', 'Khóa học cơ bản về lập trình C++', '2024-01-10', '2024-06-10', b'1'),
('Lập trình Python', 'Khóa học Python từ cơ bản đến nâng cao', '2024-02-15', '2024-07-15', b'1'),
('Lập trình Java', 'Học Java cho người mới bắt đầu', '2024-03-01', '2024-08-01', b'1'),
('Kỹ thuật Dữ liệu', 'Data Engineering với Python & SQL', '2024-04-01', '2024-09-01', b'1'),
('Lập trình Web', 'Phát triển web với HTML, CSS, JavaScript', '2024-05-01', '2024-10-01', b'1');


INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, tinh_trang) VALUES
('a', '1', b'1');
