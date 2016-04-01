package io.fourfinanceit.loan.model.response;

public class SuccessResponse {

    private Long newReference;

    public SuccessResponse(Long newReference) {
        this.newReference = newReference;
    }

	public Long getNewReference() {
		return newReference;
	}

	public void setNewReference(Long newReference) {
		this.newReference = newReference;
	}
}
