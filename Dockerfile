FROM  openjdk:17-jdk-slim
EXPOSE 9192
ADD target/ResidenceHotelBooking.jar  ResidenceHotelBooking.jar
ENTRYPOINT ["java", "-jar", "/ResidenceHotelBooking.jar"]