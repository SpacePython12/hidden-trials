package spacepython.hiddentrials.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Profiler {
    private HashMap<String, Long> times, avgTimes, timesCopy;
    private HashMap<String, Long> iterations;
    private HashMap<String, ArrayList<String>> children;
    private Stack<String> stack;

    public Profiler() {
        this.times = new HashMap<>();
        this.avgTimes = new HashMap<>();
        this.timesCopy = new HashMap<>(this.times);
        this.iterations = new HashMap<>();
        this.children = new HashMap<>();
        this.stack = new Stack<>();
    }

    public void push(String item) {
        if (this.stack.size() != 0 && !this.children.get(this.stack.peek()).contains(item)) {
            this.children.get(this.stack.peek()).add(item);
        }
        this.stack.push(item);
        if (this.children.get(item) == null) {
            this.children.put(item, new ArrayList<String>());
        }
        this.iterations.put(item, this.iterations.get(item) == null ? 1L : this.iterations.get(item) + 1L);
        this.times.put(item, System.nanoTime());
    }

    public void pop() {
        long end = System.nanoTime();
        String item = this.stack.pop();
        long start = this.times.get(item);
        long diff = end - start;
        this.times.put(item, Long.valueOf(diff));
        long lastSum = this.avgTimes.get(item) == null ? 0L : this.avgTimes.get(item) / (this.iterations.get(item) - 1L);
        this.avgTimes.put(item, (lastSum + diff) / this.iterations.get(item));
    }

    public void popPush(String item) {
        this.pop();
        this.push(item);
    }

    public void start() {
        this.push("root");
    }

    public void stop() {
        this.pop();
        this.timesCopy = null;
        this.timesCopy = new HashMap<>(this.times);
        this.times.clear();
    }

    public void reset() {
        this.avgTimes.clear();
        this.children.clear();
        this.iterations.clear();
        this.times.clear();
        this.stack.clear();
    }

    public String getAverageData() {
        return this.getChildrenData("root", 0);
    }

    private String getChildrenData(String item, int layer) {
        long totalTime;
        long accountedTime;
        long childTime;
        double percentage;
        String spacer = "    ".repeat(layer);
        String res = "";
        if (this.timesCopy == null || this.timesCopy.get(item) == null) return "";
        totalTime = this.timesCopy.get(item);
        accountedTime = 0;
        if (layer == 0) {
            res += String.format("%s (%dns):\n", item, totalTime);
        }
        for (String child: this.children.get(item)) {
            if (this.timesCopy == null || this.timesCopy.get(child) == null) continue;
            childTime = this.timesCopy.get(child);
            accountedTime += childTime;
            percentage = ((double)childTime/(double)totalTime)*100.0;
            if (Double.isNaN(percentage)) percentage = 0.0;
            percentage = Math.round(percentage*100)/100;
            res += String.format(spacer + "    %s (%dns; %s%%):\n", child, childTime, new BigDecimal(percentage).stripTrailingZeros().toPlainString());
            res += getChildrenData(child, layer+1);
        }
        return res;
    }
}