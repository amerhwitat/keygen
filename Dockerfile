FROM python:3.12-slim
WORKDIR /app
COPY . /app
RUN if [ -f requirements.txt ]; then pip install --no-cache-dir -r requirements.txt; fi
RUN useradd -m -u 10001 appuser && chown -R appuser:appuser /app
USER appuser
ENTRYPOINT ["python3"]
CMD ["-c","import os; p=next((x for x in ('main.py','app.py','run.py') if os.path.exists(x)),None); exec(open(p).read()) if p else print('Container ready; pass the application script as the command.')"]
