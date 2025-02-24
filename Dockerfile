FROM openjdk:17-jdk-slim
EXPOSE 9192

# Copy the application JAR file
ADD target/ResidenceHotelBooking.jar ResidenceHotelBooking.jar

# Allow passing SPRING_PROFILES_ACTIVE dynamically at runtime
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /ResidenceHotelBooking.jar"]
