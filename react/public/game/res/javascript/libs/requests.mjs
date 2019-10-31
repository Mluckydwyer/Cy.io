export default async function getConfig(uri) {
    let response = await fetch(uri);
    return await response.text();
}