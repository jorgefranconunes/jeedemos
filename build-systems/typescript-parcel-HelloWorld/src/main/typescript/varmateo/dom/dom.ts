function whenReady(callback: () => void): void {
    const isReady: boolean = document.readyState !== "loading";

    if (isReady) {
	callback();
    } else {
	document.addEventListener("DOMContentLoaded", callback);
    }
}

export {
    whenReady,
}
