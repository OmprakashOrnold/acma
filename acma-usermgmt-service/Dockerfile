# FROM openjdk:24-slim
FROM openjdk:17-alpine
# Define build-time variables
ARG WORK_DIR=/opt/user-mgmt-svc
ARG ARTIFACT_NAME=acma-usermgmt-service.jar
ARG SERVICE_PORT=5051

# Set environment variables
ENV FINAL_ARTIFACT_NAME=${WORK_DIR}/${ARTIFACT_NAME}

# Create working directory
RUN mkdir ${WORK_DIR}

# Set working directory
WORKDIR ${WORK_DIR}

# Copy artifact to working directory
COPY target/${ARTIFACT_NAME} ${WORK_DIR}

# Expose port
EXPOSE ${SERVICE_PORT}

# Specify command to run the Java application
CMD [ "sh", "-c", "java -jar ${FINAL_ARTIFACT_NAME}" ]
