export interface IUserspermission {
  permissionId: number;
  revoked?: boolean;
  usersUserId: number;

  permissionDescriptiveField?: string;
  usersDescriptiveField?: string;
}
