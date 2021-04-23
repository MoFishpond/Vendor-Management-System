export async function getShopInfo(params) {
  const { $http } = window;
  return $http.get('http://ec2-18-206-59-173.compute-1.amazonaws.com:8080/rest/i/vms/shop/getShopInfo', { params });
}
