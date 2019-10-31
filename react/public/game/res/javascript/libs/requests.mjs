export default async function getConfig(uri) {
    // let url = new URL(uri, window.location.href).href;
    // let response = await fetch(url);
    let response = await fetch(uri);
    return await response.text();
}