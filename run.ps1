Write-Host "===================================================" -ForegroundColor Cyan
Write-Host "   DANG KHOI CHAY HE THONG HRM KPI DSS..." -ForegroundColor Green
Write-Host "   Truy cap Dashboard: http://localhost:8088/" -ForegroundColor Yellow
Write-Host "===================================================" -ForegroundColor Cyan

if (-not $env:JAVA_HOME) {
    $env:JAVA_HOME = "C:\Program Files\Java\jdk-25"
    $env:Path = "$env:JAVA_HOME\bin;$env:Path"
}

Set-Location -Path "$PSScriptRoot\kpi-dss"
.\mvnw.cmd spring-boot:run

