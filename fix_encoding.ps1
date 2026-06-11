$path = "e:\Spring Boot\flight-booking\src\main\java\com\flightbooking\util\RedisDistributedLock.java"
$content = Get-Content $path -Encoding UTF8 -Raw
# Remove BOM if present
if ($content[0] -eq 0xFEFF) { $content = $content.Substring(1) }
# Replace garbled Chinese
$content = $content.Replace("lockKey 鍊煎緱鏄疪edis鐨刱ey requestId鎸囩殑鏄摢涓偅涓嚎绋嬬敤鐫€閿?", "lockKey 是 Redis 的 key，requestId 标识哪个线程持有锁")
$content = $content.Replace("鏈€澶氱瓑30绉?", "最多等30秒")
$content = $content.Replace("濡傛灉lockKey宸茬粡涓嶅瓨鍦燂紝鍒欒缃紝濡傛灉宸茬粡瀛樺湪锛屽垯涓嶈缃?== NX", "SETNX: key不存在才设置成功")
$content = $content.Replace("鑾峰彇閿佽涓柇", "获取锁被中断")
$content = $content.Replace("鎭㈠涓柇鐘舵€?", "恢复中断状态")
$content = $content.Replace("鎷垮埌閿侊紝绔嬪嵆杩斿洖", "拿到锁，立即返回")
$content = $content.Replace("閲婃斁閿?", "释放锁")
[System.IO.File]::WriteAllText($path, $content, [System.Text.Encoding]::UTF8)
Write-Host "Done"
