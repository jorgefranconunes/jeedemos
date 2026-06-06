function format(
    fmt: string,
    ...fmtArgs: any[]
): string {
    const getter = (...args: any[]) => {
        const index = args[1];
        return (index < fmtArgs.length) ? fmtArgs[index] : `{${index}}`;
    }

    return fmt.replace(/\{(\d+)\}/g, getter);
}

export {
    format,
}
