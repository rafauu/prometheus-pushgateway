import io.prometheus.metrics.core.metrics.Gauge;
import io.prometheus.metrics.core.metrics.Histogram;
import io.prometheus.metrics.exporter.pushgateway.PushGateway;
import io.prometheus.metrics.model.snapshots.Unit;
import java.io.IOException;

class PushGatewayApp {

  public static void main(String[] args) throws IOException {
    runSimpleTest();
  }

  private static void runSimpleTest() throws IOException {
    makeMetrics();
    PushGateway pg = PushGateway.builder().build();
    System.out.println("Pushing metrics...");
    pg.push();
    System.out.println("Push successful.");
  }

  private static void makeMetrics() {
    Histogram sizes =
        Histogram.builder()
            .name("file_sizes_bytes")
            .classicUpperBounds(256, 512, 1024, 2048)
            .unit(Unit.BYTES)
            .register();
    sizes.observe(513);
    sizes.observe(814);
    sizes.observe(1553);
    Gauge duration =
        Gauge.builder()
            .name("my_batch_job_duration_seconds")
            .help("Duration of my batch job in seconds.")
            .unit(Unit.SECONDS)
            .register();
    duration.set(0.5);
  }
}
