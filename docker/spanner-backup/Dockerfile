FROM gcr.io/google.com/cloudsdktool/google-cloud-cli:437.0.1

COPY go1.20.5.linux-amd64.tar.gz /go1.20.5.linux-amd64.tar.gz

RUN rm -rf /usr/local/go && \
	cd / && \
    tar -C /usr/local -xzf /go1.20.5.linux-amd64.tar.gz && \
    export PATH=$PATH:/usr/local/go/bin:/root/go/bin && \
    go install github.com/cloudspannerecosystem/spanner-dump@latest && \
    go install github.com/cloudspannerecosystem/spanner-cli@latest

RUN echo "export PATH=\$PATH:/usr/local/go/bin:/root/go/bin" >> /root/.bashrc && \
    mkdir /dump

COPY start.sh /start.sh
RUN chmod +x ./start.sh

ENTRYPOINT exec /start.sh
