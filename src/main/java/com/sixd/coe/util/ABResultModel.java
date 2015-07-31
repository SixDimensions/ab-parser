package com.sixd.coe.util;

public class ABResultModel {

	private String concurrencyLevel;
	private String completedRequests;
	private String exceptions;
	private final String fileName;

	private String requestsPerSecond;

	private String timePerRequest;
	private String fiftiethPercentile;
	private String seventyfifthPercentile;
	private String ninetiethPercentile;
	private String ninetyfifthPercentile;
	private String longest;

	public ABResultModel(String fileName) {
		this.fileName = fileName;
	}

	@OutputCfg(title = "Completed Requests")
	public String getCompletedRequests() {
		return completedRequests;
	}

	@OutputCfg(title = "Concurrency Level")
	public String getConcurrencyLevel() {
		return concurrencyLevel;
	}

	@OutputCfg(title = "Exceptions")
	public String getExceptions() {
		return exceptions;
	}

	@OutputCfg(title = "50th Percentile")
	public String getFiftiethPercentile() {
		return fiftiethPercentile;
	}

	@OutputCfg(title = "File Name")
	public String getFileName() {
		return fileName;
	}

	@OutputCfg(title = "100th Percentile")
	public String getLongest() {
		return longest;
	}

	@OutputCfg(title = "90th Percentile")
	public String getNinetiethPercentile() {
		return ninetiethPercentile;
	}

	@OutputCfg(title = "95th Percentile")
	public String getNinetyfifthPercentile() {
		return ninetyfifthPercentile;
	}

	@OutputCfg(title = "Requests Per Second")
	public String getRequestsPerSecond() {
		return requestsPerSecond;
	}

	@OutputCfg(title = "75th Percentile")
	public String getSeventyfifthPercentile() {
		return seventyfifthPercentile;
	}

	@OutputCfg(title = "Time per Request")
	public String getTimePerRequest() {
		return timePerRequest;
	}

	@ParseCfg(test = "^Complete requests.+", startAfter = ":")
	public void setCompletedRequests(String completedRequests) {
		this.completedRequests = completedRequests;
	}

	@ParseCfg(test = "^Concurrency Level.+", startAfter = ":")
	public void setConcurrencyLevel(String concurrencyLevel) {
		this.concurrencyLevel = concurrencyLevel;
	}

	@ParseCfg(test = "^.+Exceptions: [0-9]+\\)$", startAfter = "Exceptions:", terminateAt = ")")
	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}

	@ParseCfg(test = "^  50%.+\\d+", startAfter = "%")
	public void setFiftiethPercentile(String fiftiethPercentile) {
		this.fiftiethPercentile = fiftiethPercentile;
	}

	@ParseCfg(test = "^ 100%.+", startAfter = "%", terminateAt = "(")
	public void setLongest(String longest) {
		this.longest = longest;
	}

	@ParseCfg(test = "^  90%.+\\d+", startAfter = "%")
	public void setNinetiethPercentile(String ninetiethPercentile) {
		this.ninetiethPercentile = ninetiethPercentile;
	}

	@ParseCfg(test = "^  95%.+\\d+", startAfter = "%")
	public void setNinetyfifthPercentile(String ninetyfifthPercentile) {
		this.ninetyfifthPercentile = ninetyfifthPercentile;
	}

	@ParseCfg(test = "^Requests per second.+", startAfter = ":", terminateAt = "[")
	public void setRequestsPerSecond(String requestsPerSecond) {
		this.requestsPerSecond = requestsPerSecond;
	}

	@ParseCfg(test = "^  75%.+\\d+", startAfter = "%")
	public void setSeventyfifthPercentile(String seventyfifthPercentile) {
		this.seventyfifthPercentile = seventyfifthPercentile;
	}

	@ParseCfg(test = "^Time per request:.+\\(mean\\)$", startAfter = ":", terminateAt = "[")
	public void setTimePerRequest(String timePerRequest) {
		this.timePerRequest = timePerRequest;
	}

	@Override
	public String toString() {
		return "ABResultModel [concurrencyLevel=" + concurrencyLevel
				+ ", completedRequests=" + completedRequests + ", exceptions="
				+ exceptions + ", fileName=" + fileName
				+ ", requestsPerSecond=" + requestsPerSecond
				+ ", timePerRequest=" + timePerRequest
				+ ", fiftiethPercentile=" + fiftiethPercentile
				+ ", seventyfifthPercentile=" + seventyfifthPercentile
				+ ", ninetiethPercentile=" + ninetiethPercentile
				+ ", ninetyfifthPercentile=" + ninetyfifthPercentile
				+ ", longest=" + longest + "]";
	}

}
