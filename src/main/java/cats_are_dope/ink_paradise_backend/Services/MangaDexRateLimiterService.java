package cats_are_dope.ink_paradise_backend.Services;

import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.*;
import org.springframework.stereotype.Service;

@Service
public class MangaDexRateLimiterService {
  private final RateLimiter rateLimiter = RateLimiter.create(5.0); // 5 requests per second
  private final BlockingQueue<Runnable> requestQueue = new LinkedBlockingQueue<>();
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  public MangaDexRateLimiterService() {
    startQueueProcessor();
  }

  // Submit tasks to the queue
  public void processRequest(Runnable task) {
    if (rateLimiter.tryAcquire()) {
      // If rate limit allows, execute immediately
      task.run();
    } else {
      // Otherwise, queue the task for later execution
      requestQueue.offer(task);
    }
  }

  // Background thread to process queued tasks at a controlled rate
  private void startQueueProcessor() {
    executorService.submit(
        () -> {
          while (true) {
            try {
              Runnable task = requestQueue.take(); // Take next task from queue
              rateLimiter.acquire(); // Wait until a permit is available
              task.run(); // Execute the task
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              break;
            }
          }
        });
  }
}
