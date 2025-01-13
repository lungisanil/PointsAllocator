
FROM amazoncorretto:17-alpine-jdk
COPY target/PointsAllocator-1.0.0-SNAPSHOT.war PointsAllocator.war
ENTRYPOINT ["java", "-jar", "/PointsAllocator.war"]

#docker build -t points-allocator-image .