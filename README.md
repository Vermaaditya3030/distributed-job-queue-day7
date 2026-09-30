# Day 7 — Distributed Job Queue

Java 17 + Spring Boot + Redis + Docker + Prometheus + Grafana.

## Architecture
Client → Job Service → Redis Queue → Worker 1/Worker 2 → Redis Job State

The worker pool demonstrates concurrent consumers, retries, idempotent state storage, and a Dead Letter Queue.

## Run on Windows PowerShell
```powershell
docker compose up --build
```

Create a successful job:
```powershell
curl.exe -X POST http://localhost:8081/api/jobs -H "Content-Type: application/json" -d '{"type":"EMAIL","payload":"Welcome to the platform"}'
```

Copy the returned jobId and query it:
```powershell
curl.exe http://localhost:8081/api/jobs/JOB-XXXXXXXX
```

Simulate a failure/retry:
```powershell
curl.exe -X POST http://localhost:8081/api/jobs -H "Content-Type: application/json" -d '{"type":"FAIL","payload":"simulate failure"}'
```
After three attempts, the job ID is placed in `jobs:dead`.

Health:
```powershell
curl.exe http://localhost:8081/api/jobs/health
curl.exe http://localhost:8083/api/notifications
```

Prometheus: http://localhost:9090
Grafana: http://localhost:3000 (admin/admin)

## Redis queues
- `jobs:queue` — pending work
- `jobs:completed` — completed IDs
- `jobs:dead` — jobs that exhausted retries
- `job:<id>` — current job state

## Stop
```powershell
docker compose down
```
Reset Redis too:
```powershell
docker compose down -v
```

## GitHub
```powershell
git init
git add .
git commit -m "feat: build distributed job queue"
git branch -M main
git remote add origin git@github.com:Vermaaditya3030/distributed-job-queue.git
git push -u origin main
```
