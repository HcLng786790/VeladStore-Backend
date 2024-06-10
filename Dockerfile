# Sử dụng hình ảnh OpenJDK để chạy ứng dụng
FROM openjdk:17-jdk-alpine

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép tất cả các file JAR từ thư mục target vào container
COPY target/veladstore-0.0.1-SNAPSHOT.jar velad.jar

# Mở cổng 8080 để ứng dụng có thể nhận các kết nối
EXPOSE 8080

# Định nghĩa lệnh để chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "velad.jar"]
#ENTRYPOINT ["./wait-for-it.sh", "veladb:3306", "--", "java", "-jar", "velad.jar"]

