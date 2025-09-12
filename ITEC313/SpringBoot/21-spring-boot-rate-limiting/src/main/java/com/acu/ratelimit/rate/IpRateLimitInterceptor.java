package com.acu.ratelimit.rate;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
// Using Bucket.builder() in recent versions
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IpRateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, Bucket> ipToBucket = new ConcurrentHashMap<>();

    private Bucket newBucket() {
        Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofSeconds(10)));
        return Bucket.builder().addLimit(limit).build();
    }

    private Bucket resolveBucket(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        return ipToBucket.computeIfAbsent(ip, k -> newBucket());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Allow health endpoint without limits
        String path = request.getRequestURI();
        if (path != null && path.startsWith("/api/rl/health")) {
            return true;
        }
        Bucket bucket = resolveBucket(request);
        if (bucket.tryConsume(1)) {
            return true;
        }
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.getWriter().write("Rate limit exceeded\n");
        return false;
    }
}


