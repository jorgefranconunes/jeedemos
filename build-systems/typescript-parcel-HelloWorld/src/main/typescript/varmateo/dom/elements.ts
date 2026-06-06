function withId(elementId: string): Element {
    const selector: string = `#${elementId}`;
    const element: Element | null = document.querySelector(selector);

    if ( element == null ) {
        const msg = `No element with ID "${elementId}"`;
        throw msg;
    }

    return element;
}

export {
    withId,
}
