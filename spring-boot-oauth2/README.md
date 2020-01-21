<https://zhuanlan.zhihu.com/p/96512158>

### 请求token

请求所需参数：client_id、client_secret、grant_type、username、password
```http request
POST http://localhost:1300/oauth/token?client_id=demoClientId&client_secret=demoClientSecret&grant_type=password&username=demoUser&password=50575tyL86xp29O380t1
```

### 检查token

请求所需参数：token
```http request
POST http://localhost:1300/oauth/check_token?token=f57ce129-2d4d-4bd7-1111-f31ccc69d4d1
```

### 刷新token

请求所需参数：grant_type、refresh_token、client_id、client_secret 
其中grant_type为固定值：grant_type=refresh_token
```http request
POST http://localhost:1300/oauth/token?grant_type=refresh_token&refresh_token=fbde81ee-f419-42b1-1234-9191f1f95be9&client_id=demoClientId&client_secret=demoClientSecret
```
### 端口请求
所需参数 `access_token`
access_token 认证后的token
```aidl
POST http://localhost:1300/user?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJYLVRvb2xzLVVzZXJJZCI6bnVsbCwidXNlcl9uYW1lIjoiZGVtb1VzZXIiLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNTY3NDc0ODE0LCJhdXRob3JpdGllcyI6WyJmaW5kY2xpZW50Il0sImp0aSI6IjAyN2UxNzEyLWVhOGEtNGExMi04YTE0LTdlZWZiYTU0MDZiNSIsImNsaWVudF9pZCI6ImRlbW9DbGllbnRJZCIsIlgtVG9vbHMtQ2xpZW50SWQiOm51bGx9.KKIvcJn8rnBkojuQQAfH69yEJ3_eDFWAmDPvMWIi68s
```
